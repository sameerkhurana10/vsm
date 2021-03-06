package VSMTests;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import no.uib.cipr.matrix.DenseVector;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import VSMConstants.VSMContant;
import VSMSerialization.VSMReadSerialObject;
import VSMSerialization.VSMSentenceEmbeddingBean;
import VSMUtilityClasses.VSMUtil;

public class TestReadSerializeSentenceVec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 914973760707369149L;

	public static void main(String... args) throws IOException {

		System.out.println("***newly compiled**");

		PrintWriter writer = new PrintWriter(VSMContant.SIMILARITY_SCORE_DIFF,
				"UTF-8");

		// PrintWriter out = new PrintWriter(
		// "/afs/inf.ed.ac.uk/group/project/vsm/sentencesim/similaritydiff.txt");

		double[] modelScores = new double[4500];

		// double[] newsim = new double[4500];

		String sentenceVecDirec = VSMContant.SICK_SENTENCE_EMBED_SYNTACTIC;

		/*
		 * Getting all the sentence vectors
		 */
		File[] vectorFiles = new File(sentenceVecDirec)
				.listFiles(new FileFilter() {
					@Override
					public boolean accept(File file) {
						return !file.isHidden();
					}
				});
		sortByNumber(vectorFiles);

		int count = 0;
		for (int i = 0; i < vectorFiles.length; i += 2) {

			// System.out.println(i);

			/*
			 * Sentence pairs
			 */
			File sentenceVecFile1 = vectorFiles[i];
			File sentenceVecFile2 = vectorFiles[i + 1];

			System.out.println("1::" + sentenceVecFile1.getName() + " 2::"
					+ sentenceVecFile2.getName());

			// System.out.println("***Finding similarity between**"
			// + sentenceVecFile1.getName() + " and "
			// + sentenceVecFile2.getName());

			// DenseVector sentence1 = VSMReadSerialObject
			// .readSerializedSentenceVec(
			// sentenceVecFile1.getAbsolutePath())
			// .getSentenceVector();

			DenseVector sentence1 = VSMReadSerialObject
					.readSerializedSentenceVec(
							sentenceVecFile1.getAbsolutePath())
					.getSentenceVector();
			// System.out.println(sentence1);
			// double norm1 = VSMUtil.norm2(sentence1.getData());

			// DenseVector sentence2 = VSMReadSerialObject
			// .readSerializedSentenceVec(
			// sentenceVecFile2.getAbsolutePath())
			// .getSentenceVector();

			DenseVector sentence2 = VSMReadSerialObject
					.readSerializedSentenceVec(
							sentenceVecFile2.getAbsolutePath())
					.getSentenceVector();
			// System.out.println(sentence2);
			// double norm2 = VSMUtil.norm2(sentence2.getData());

			/*
			 * Calculating similarity
			 */
			// double dot = Math.abs(sentence1.dot(sentence2));
			// double divisor = norm1 * norm2;
			// System.out.println((dot / divisor) * 5);

			double similarityScore = VSMUtil.cosineSimilarity(
					sentence1.getData(), sentence2.getData());

			if (similarityScore < 1.0) {
				modelScores[count] = 1.0;
			} else {
				modelScores[count] = similarityScore;
			}
			count++;

		}

		for (double score : modelScores) {
			// System.out.println(score);
			break;
		}

		Collection<Double> goldSTandard = VSMUtil.getGoldStandard().values();

		@SuppressWarnings({ "rawtypes", "unchecked" })
		List list = new ArrayList(goldSTandard);

		double[] goldScores = new double[list.size()];

		for (int i = 0; i < list.size(); i++)
			goldScores[i] = (double) list.get(i);

		PearsonsCorrelation corr = new PearsonsCorrelation();

		System.out.println(corr.correlation(goldScores, modelScores));

	}

	public static void sortByNumber(File[] files) {
		Arrays.sort(files, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				int n1 = extractNumer(o1.getName());
				int n2 = extractNumer(o2.getName());
				return n1 - n2;
			}

			private int extractNumer(String name) {

				int i = 0;

				try {
					int s = name.indexOf('_') + 1;
					int e = name.lastIndexOf('.');
					String number = name.substring(s, e);
					i = Integer.parseInt(number);
				} catch (Exception e) {
					i = 0;
				}
				return i;

			}
		});
	}
}
