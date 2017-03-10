/**
 * 
 */
package calculations;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import de.uhd.ifi.feature.metric.calculator.calculations.FeatureCalculator;
import de.uhd.ifi.feature.metric.calculator.data.Feature;

// TODO: Auto-generated Javadoc
/**
 * The Class CalculateMetricsTest.
 *
 * @author Rafael Gerner
 */
public class FeatureCalculatorTest {

	/** The exception. */
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	/** The calculator. */
	private FeatureCalculator calculator;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		calculator = new FeatureCalculator();
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
		calculator = null;
	}

	/**
	 * Test method for
	 * {@link de.uhd.ifi.feature.metric.calculator.calculations.CalculateMetrics#getClassesList(java.io.File)}
	 * with a Project that has no class with a 'Feature' annotation. Therefore
	 * the total lines of code of implemented feature should be 0.
	 */
	@Test
	public void testGetClassesListNoAnnotations() {
		File projectDir = new File("TestStubNoAnn");
		List<Feature> featureList = calculator.getClassesList(projectDir);
		assertNotNull(featureList);
		assertTrue(featureList.isEmpty());
		int totalLines = calculator.getTotalLines(featureList);
		assertTrue(totalLines == 0);
	}

	/**
	 * Test method for
	 * {@link de.uhd.ifi.feature.metric.calculator.calculations.CalculateMetrics#getClassesList(java.io.File)}
	 * with a Project that has one class with a 'Feature' annotation. The
	 * 'Feature' annotation contains exactly 1 feature. Meaning the class
	 * implements exactly 1 feature. That class has 36 lines of code. Therefore
	 * the total lines of code of implemented class features should be 36.
	 */
	@Test
	public void testGetClassesListOneAnnotationSingleFeature() {
		File projectDir = new File("test/testStubOneAnn");
		List<Feature> featureList = calculator.getClassesList(projectDir);
		assertNotNull(featureList);
		assertFalse(featureList.isEmpty());
		assertTrue(featureList.size() == 1);
		assertNotNull(featureList.get(0));
		assertTrue(featureList.get(0).getLoc() == 36);
		int totalLines = calculator.getTotalLines(featureList);
		assertTrue(totalLines == 36);
	}

	/**
	 * Test method for
	 * {@link de.uhd.ifi.feature.metric.calculator.calculations.CalculateMetrics#getClassesList(java.io.File)}
	 * with a Project that has one class with a 'Feature' annotation. The
	 * 'Feature' annotation contains exactly 3 features. Meaning the class
	 * implements 3 features. That class has 343 lines of code. Therefore the
	 * total lines of code of implemented class features should be 1029.
	 */
	@Test
	public void testGetClassesListOneAnnotationMultipleFeatures() {
		File projectDir = new File("test/testStubMultipleFeatAnn");
		List<Feature> featureList = calculator.getClassesList(projectDir);
		assertNotNull(featureList);
		assertFalse(featureList.isEmpty());
		assertTrue(featureList.size() == 3);
		int totalLines = 0;
		for (Feature feature : featureList) {
			assertNotNull(feature);
			assertTrue(feature.getLoc() == 343);
			totalLines += feature.getLoc();
		}
		int totalLinesCalc = calculator.getTotalLines(featureList);
		assertTrue(totalLines == totalLinesCalc);
	}

	/**
	 * Test method for
	 * {@link de.uhd.ifi.feature.metric.calculator.calculations.CalculateMetrics#getClassesList(java.io.File)}
	 * with a project that more than one class with 'Feature' annotations. The
	 * project contains exactly 5 different class feature annotations.
	 */
	@Test
	public void testGetClassesListMoreThanOneAnnotation() {
		File projectDir = new File("test/testStubMoreThanOne");
		List<Feature> featureList = calculator.getClassesList(projectDir);
		assertNotNull(featureList);
		assertFalse(featureList.isEmpty());
		int totalLines = 0;
		for (Feature feature : featureList) {
			assertNotNull(feature);
			totalLines += feature.getLoc();
		}

		int totalLinesCalc = calculator.getTotalLines(featureList);
		assertTrue(totalLines == totalLinesCalc);
		assertTrue(featureList.size() == 5);
	}

	/**
	 * Test method for
	 * {@link de.uhd.ifi.feature.metric.calculator.calculations.CalculateMetrics#getMethodsList(java.io.File)}
	 * with a Project that has no method with a 'Feature' annotation.
	 */
	@Test
	public void testGetMethodsListNoAnnotations() {
		File projectDir = new File("test/TestStubNoAnn");
		List<Feature> featureList = calculator.getMethodsList(projectDir);
		assertNotNull(featureList);
		assertTrue(featureList.isEmpty());
		int totalLines = calculator.getTotalLines(featureList);
		assertTrue(totalLines == 0);
	}

	/**
	 * Test method for
	 * {@link de.uhd.ifi.feature.metric.calculator.calculations.CalculateMetrics#getMethodsList(java.io.File)}
	 * with a Project that has one method with a 'Feature' annotation. That
	 * method has 3 lines of code.
	 */
	@Test
	public void testGetMethodsListOneAnnotation() {
		File projectDir = new File("test/testStubOneAnn");
		List<Feature> featureList = calculator.getMethodsList(projectDir);
		assertNotNull(featureList);
		assertFalse(featureList.isEmpty());
		assertTrue(featureList.size() == 1);
		assertNotNull(featureList.get(0));
		assertTrue(featureList.get(0).getLoc() == 3);
		int totalLines = calculator.getTotalLines(featureList);
		assertTrue(totalLines == 3);
	}

	/**
	 * Test method for
	 * {@link de.uhd.ifi.feature.metric.calculator.calculations.CalculateMetrics#getMethodsList(java.io.File)}
	 * with a Project that more than one 'Feature' method annotation. The
	 * project contains exactly 5 different method feature annotations.
	 */
	@Test
	public void testGetMethodsListMoreThanOneAnnotation() {
		File projectDir = new File("test/testStubMoreThanOne");
		List<Feature> featureList = calculator.getMethodsList(projectDir);
		assertNotNull(featureList);
		assertFalse(featureList.isEmpty());
		int totalLines = 0;
		for (Feature feature : featureList) {
			assertNotNull(feature);
			totalLines += feature.getLoc();
		}
		int totalLinesCalc = calculator.getTotalLines(featureList);
		assertTrue(totalLines == totalLinesCalc);
		assertTrue(featureList.size() == 5);
	}

	/**
	 * Test method for
	 * {@link de.uhd.ifi.feature.metric.calculator.calculations.CalculateMetrics#getLinesRatio(int, int)}.
	 * Test with 2 positive numbers: 454 and 226. Therefore the ratio is:
	 * 227:113
	 */
	@Test
	public void testGetLinesRatioBothPositive() {
		int locSrc = 454;
		int locTest = 226;
		assertTrue(calculator.getLinesRatio(locSrc, locTest).equals("227:113"));
	}

	/**
	 * Test method for
	 * {@link de.uhd.ifi.feature.metric.calculator.calculations.CalculateMetrics#getLinesRatio(int, int)}.
	 * Test with first number positive and second 0: 454 and 0. Therefore the
	 * ratio is: 1:0
	 */
	@Test
	public void testGetLinesRatioSecondZero() {
		int locSrc = 454;
		int locTest = 0;
		assertTrue(calculator.getLinesRatio(locSrc, locTest).equals("1:0"));
	}

	/**
	 * Test method for
	 * {@link de.uhd.ifi.feature.metric.calculator.calculations.CalculateMetrics#getLinesRatio(int, int)}.
	 * Test with first number positive and second 0: 0 and 226. Therefore the
	 * ratio is: 0:1
	 */
	@Test
	public void testGetLinesRatioFirstZero() {
		int locSrc = 0;
		int locTest = 226;
		assertTrue(calculator.getLinesRatio(locSrc, locTest).equals("0:1"));
	}
}
