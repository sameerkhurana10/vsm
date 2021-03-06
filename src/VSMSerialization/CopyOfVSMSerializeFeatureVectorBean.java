package VSMSerialization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Set;

public class CopyOfVSMSerializeFeatureVectorBean {

	private static LinkedHashMap<String, Integer> countMap;
	private static int count;
	private static int fileIdx;

	/*
	 * No args constructor
	 */
	public CopyOfVSMSerializeFeatureVectorBean() {
		countMap = new LinkedHashMap<String, Integer>();
		count = 0;
	}

	/*
	 * Constructor with arguments, fetch the count map and send it to this
	 * constructor, so that the count can start making files from where we left
	 * off in the previous iteration
	 */

	public CopyOfVSMSerializeFeatureVectorBean(
			LinkedHashMap<String, Integer> countMap) {
		CopyOfVSMSerializeFeatureVectorBean.countMap = countMap;
		count = 0;
	}

	public void serializeVectorBean(VSMFeatureVectorBean vectorBean) {
		Set<String> labels = countMap.keySet();
		/*
		 * If the map already contains the label then get the count variable
		 */
		if (labels.contains(vectorBean.getLabel())) {
			count = countMap.get(vectorBean.getLabel());
			count += 1;
		} else {

			count = 1;
		}
		/*
		 * The put method replaces the value of the existing key
		 */
		countMap.put(vectorBean.getLabel(), count);

		/*
		 * Taking 300k samples of each node and no more
		 */

		// File file = new
		// File("/Users/sameerkhurana10/Documents/serialization/"
		// + phiBean.getLabel());
		/*
		 * Location where sparse feature vectors are storeds
		 */
		File file = null;
		if (count <= 30000) {

			file = new File(
					"/afs/inf.ed.ac.uk/group/project/vsm/sparsefeaturevectors1/"
							+ vectorBean.getLabel() + "/"
							+ vectorBean.getLabel() + "_1");
		} else if (count <= 60000) {

			file = new File(
					"/afs/inf.ed.ac.uk/group/project/vsm/sparsefeaturevectors1/"
							+ vectorBean.getLabel() + "/"
							+ vectorBean.getLabel() + "_2");
		} else if (count <= 90000) {

			file = new File(
					"/afs/inf.ed.ac.uk/group/project/vsm/sparsefeaturevectors1/"
							+ vectorBean.getLabel() + "/"
							+ vectorBean.getLabel() + "_3");
		} else if (count <= 120000) {

			file = new File(
					"/afs/inf.ed.ac.uk/group/project/vsm/sparsefeaturevectors1/"
							+ vectorBean.getLabel() + "/"
							+ vectorBean.getLabel() + "_4");
		} else if (count <= 150000) {

			file = new File(
					"/afs/inf.ed.ac.uk/group/project/vsm/sparsefeaturevectors1/"
							+ vectorBean.getLabel() + "/"
							+ vectorBean.getLabel() + "_5");
		} else if (count <= 180000) {

			file = new File(
					"/afs/inf.ed.ac.uk/group/project/vsm/sparsefeaturevectors1/"
							+ vectorBean.getLabel() + "/"
							+ vectorBean.getLabel() + "_6");
		} else if (count <= 210000) {

			file = new File(
					"/afs/inf.ed.ac.uk/group/project/vsm/sparsefeaturevectors1/"
							+ vectorBean.getLabel() + "/"
							+ vectorBean.getLabel() + "_7");
		} else if (count <= 240000) {

			file = new File(
					"/afs/inf.ed.ac.uk/group/project/vsm/sparsefeaturevectors1/"
							+ vectorBean.getLabel() + "/"
							+ vectorBean.getLabel() + "_8");
		} else if (count <= 270000) {

			file = new File(
					"/afs/inf.ed.ac.uk/group/project/vsm/sparsefeaturevectors1/"
							+ vectorBean.getLabel() + "/"
							+ vectorBean.getLabel() + "_9");
		} else if (count <= 300000) {

			file = new File(
					"/afs/inf.ed.ac.uk/group/project/vsm/sparsefeaturevectors1/"
							+ vectorBean.getLabel() + "/"
							+ vectorBean.getLabel() + "_10");
		} else {

			System.out.println("**Done***");
		}

		if (file != null) {
			if (!file.exists()) {
				file.mkdirs();
				fileIdx = 1;
			}

			/*
			 * The .ser file name
			 */
			String filename = file.getAbsolutePath() + "/"
					+ vectorBean.getLabel() + "_" + fileIdx + ".ser";
			// String filename =
			// "/Users/sameerkhurana10/Documents/testserialization/"
			// + phiBean.getLabel() + "_1.ser";

			/*
			 * Serializing the object
			 */
			FileOutputStream fos = null;
			ObjectOutputStream out = null;

			try {

				fos = new FileOutputStream(filename, false);
				out = new ObjectOutputStream(fos);
				out.writeObject(vectorBean);
				fileIdx++;
				out.close();
				fos.close();

			} catch (IOException ex) {

				System.err.println("***File name too large***");
			}
		} else {
			System.out.println("****Done no more serialization***");
		}

	}

	/*
	 * Method that returns the count map, used to retrieve the count map at the
	 * end of the operation on one particular file, so that we can serialize the
	 * count map for the next operation
	 */
	public static LinkedHashMap<String, Integer> getCountMap() {

		return countMap;

	}

}
