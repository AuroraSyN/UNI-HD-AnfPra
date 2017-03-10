 /** Copyright (c) 2017 FMC-Team1 and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html *
 * Contributors:IBM Corporation - initial API and implementation */
package de.uhd.ifi.feature.metric.calculator.calculations;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.math.*;

import de.uhd.ifi.feature.metric.calculator.data.Feature;
import de.uhd.ifi.feature.metric.calculator.data.Type;
import de.uhd.ifi.feature.metric.calculator.support.DirExplorer;

// Final. Version 1.0.0
/**
 * Provides methods for finding @Feature annotations in a project
 * and calculating metrics based on these annotations.
 * @author Rafael Gerner *
 */
public class FeatureCalculator {

	/** The feature list classes. */
	private List<Feature> classesList = new ArrayList<Feature>();
	
	/** The feature list methods. */
	private List<Feature> methodsList = new ArrayList<Feature>();

	/**
	 * Finds all Class annotations in the project.
	 * 
	 * @param projectDir
	 *            the project to calculate metrics on
	 * @return the list with all features of that project.
	 */
	public List<Feature> getClassesList(final File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			try {
				new VoidVisitorAdapter<Object>() {
					@Override
					/**
					 * override visit(n, arg)
					 * @param n
					 * @param arg
					 */
					public void visit(final ClassOrInterfaceDeclaration n, final Object arg) {
						super.visit(n, arg);
						final Type type = Type.ClassFeature;
						handleFeature(n, classesList, type);
					}
				}.visit(JavaParser.parse(file), null);
			} catch (ParseException | IOException e) {
				new RuntimeException(e);
			}

		}).explore(projectDir);
		return classesList;
	}

	/**
	 * Finds all Method annotations in the project.
	 *
	 * @param projectDir            the project to calculate metrics on
	 * @return the methods list
	 */
	public List<Feature> getMethodsList(final File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
			try {
				new VoidVisitorAdapter<Object>() {
					/**
					 * overrides visit(n, arg).
					 * @param n
					 * @param arg
					 */
					@Override
					public void visit(final MethodDeclaration n, final Object arg) {
						super.visit(n, arg);
						final Type type = Type.MethodFeature;
						handleFeature(n, methodsList, type);
					}
				}.visit(JavaParser.parse(file), null);
			} catch (ParseException | IOException e) {
				new RuntimeException(e);
			}

		}).explore(projectDir);
		return methodsList;
	}

	/**
	 * Filters for '@Feature' annotations and calculates the lines of code for
	 * each feature. Splits multiple Feature annotations into single features.	 * 
	 *
	 * @param bodyDec the body dec
	 * @param featureList the feature list
	 * @param type the type
	 */
	public void handleFeature(final BodyDeclaration bodyDec, final List<Feature> featureList, final Type type) {
		if (bodyDec == null) {
			return;
		}
		for (final AnnotationExpr annotation : bodyDec.getAnnotations()) {
			if (annotation.getName().toString().equals("Feature")) {
				String featureName = annotation.getChildrenNodes().get(1).toString();
				final int loc = bodyDec.getEnd().line - bodyDec.getBegin().line;

				if (annotation.getChildrenNodes().get(1).getChildrenNodes().size() > 0) {
					for (int i = 0; i < annotation.getChildrenNodes().get(1).getChildrenNodes().size(); i++) {
						featureName = annotation.getChildrenNodes().get(1).getChildrenNodes().get(i).toString();
						insertFeature(featureList, featureName, loc, type);
					}
				} else {
					featureName = annotation.getChildrenNodes().get(1).toString();
					if (!bodyDec.getChildrenNodes().isEmpty()) {
						insertFeature(featureList, featureName, loc, type);
					}
				}
			}
		}
	}

	/**
	 * Creates and inserts features into a list. If a feature is already in the
	 * list only the loc of the feature will be updated.	 * 
	 *
	 * @param featureList the feature list
	 * @param featureName the feature name
	 * @param loc the loc
	 * @param type the type
	 */
	public void insertFeature(final List<Feature> featureList, final String featureName, final int loc, final Type type) {
		boolean isInList = false;
		int index = 0;
		for (int i = 0; i < featureList.size(); i++) {
			if (featureList.get(i).getName().equals(featureName)) {
				isInList = true;
				index = i;
			}
		}
		if (isInList) {
			final int oldLines = featureList.get(index).getLoc();
			featureList.get(index).setLoc(loc + oldLines);
		} else {
			final Feature newFeature = new Feature(featureName, loc, type);
			featureList.add(newFeature);
		}
	}

	/**
	 * Deletes all values from the lists.
	 *
	 * @param featureList the feature list
	 * @return the total lines
	 */
	/**
	 * Calculates the total number of Lines of Code for a list of features.
	 * 
	 * @param a
	 *            list of features.
	 * @return the total lines of code of a list of features.
	 */
	public int getTotalLines(final List<Feature> featureList) {
		int totalLines = 0;
		for (final Feature feature : featureList) {
			totalLines += feature.getLoc();
		}
		return totalLines;
	}

	/**
	 * Calculates the ratio between to numbers. Is used for calculating the
	 * ratio between the lines of source and test code of a feature.	 * 
	 *
	 * @param locSrc the loc src
	 * @param locTest the loc test
	 * @return the lines ratio
	 */
	public String getLinesRatio(final int locSrc, final int locTest) {
		final BigInteger bigInt1 = new BigInteger("" + locSrc);
		final BigInteger bigInt2 = new BigInteger("" + locTest);
		String ratio = "";

		try {
			final BigInteger gcd = bigInt1.gcd(bigInt2);
			ratio = (locSrc / gcd.intValue()) + ":" + (locTest / gcd.intValue());
		} catch (ArithmeticException ae) {
			ae.printStackTrace();
		}

		return ratio;
	}
}