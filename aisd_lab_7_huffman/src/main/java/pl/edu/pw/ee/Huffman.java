package pl.edu.pw.ee;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Huffman {

    private final String ORIGIN_FILENAME = "org.txt";
    private final String COMPRESSED_FILENAME = "compressed.jp2";
    private final String DECOMPRESSED_FILENAME = "decompressed.txt";
    private final String TREE_FILENAME = "tree.jp2";

    private class Node implements Comparable<Node> {

	private Integer incidence;
	private Character character;
	private Node left = null;
	private Node right = null;

	public int compareTo(Huffman.Node n) {
	    return incidence.compareTo(n.incidence);
	}

	public Node(Character character, Integer incidence) {
	    this.character = character;
	    this.incidence = incidence;
	}
    }

    private int readPos = 0;

    public int huffman(String pathToRootDir, boolean compress) {
	if (pathToRootDir == null) {
	    throw new IllegalArgumentException("Path to root directory cannot be null");
	}
	char[] fileChars = null;
	if (compress) {
	    return huffmanCompress(fileChars, pathToRootDir);
	} else {
	    return huffmanDecompress(pathToRootDir);
	}
    }

    private int huffmanDecompress(String pathToRootDir) throws IllegalArgumentException, IllegalStateException {
	byte[] compTreeBytes = null;
	try {
	    compTreeBytes = Files.readAllBytes(Paths.get(pathToRootDir + TREE_FILENAME));
	} catch (Exception e) {
	    throw new IllegalArgumentException("Input file cannot be read properly");
	}
	Node huffTree = reconstructHuffmanTree(BitSet.valueOf(compTreeBytes));
	List<Character> decompFileChars = null;
	try {
	    decompFileChars = decompFileWithHuffTree(pathToRootDir + COMPRESSED_FILENAME, huffTree);
	    FileWriter fileWriter = new FileWriter(new File(pathToRootDir + DECOMPRESSED_FILENAME));
	    for (Character c : decompFileChars) {
		if (c == '\n') {
		    String newLine = System.lineSeparator();
		    for (int i = 0; i < newLine.length(); i++) {
			fileWriter.write(newLine.charAt(i));
		    }
		    continue;
		}
		fileWriter.write(c.charValue());
	    }
	    fileWriter.close();
	} catch (Exception e) {
	    throw new IllegalStateException("Problem occured with creating the output file");
	}
	return decompFileChars.size();
    }

    private int huffmanCompress(char[] fileChars, String pathToRootDir) throws IllegalArgumentException, IllegalStateException {
	try {
	    fileChars = readFileIntoCharArr(pathToRootDir + ORIGIN_FILENAME);
	} catch (Exception e) {
	    throw new IllegalArgumentException("Input file cannot be read properly");
	}
	Queue<Node> nodeQueue = buildPriorityQueueOfFileChars(fileChars);
	Node huffTree = buildHuffmanTree(nodeQueue);
	Map<Character, String> charCodes = new HashMap<Character, String>();
	buildCharacterCodes(huffTree, "", charCodes);
	try {
	    return buildCompressedFile(charCodes, fileChars, huffTree, pathToRootDir);
	} catch (Exception e) {
	    throw new IllegalStateException("Problem occured with creating the output file");
	}
    }

    private char[] readFileIntoCharArr(String path) throws FileNotFoundException {
	String buffer = "";
	File file = new File(path);
	Scanner scanner;
	scanner = new Scanner(file);
	buffer = scanner.nextLine();
	String readLine = "";
	while (scanner.hasNextLine()) {
	    readLine = scanner.nextLine();
	    buffer = buffer + "\n" + readLine;
	}
	if (readLine == "") {
	    buffer += "\n";
	}
	return buffer.toCharArray();
    }

    private Queue<Node> buildPriorityQueueOfFileChars(char[] fileChars) {
	Map<Character, Integer> charOccur = new HashMap<Character, Integer>();
	for (char cha : fileChars) {
	    if (charOccur.containsKey(cha)) {
		charOccur.put(cha, charOccur.get(cha) + 1);
	    } else {
		charOccur.put(cha, 1);
	    }
	}
	Queue<Node> queue = new PriorityQueue<Node>();
	for (Map.Entry<Character, Integer> pair : charOccur.entrySet()) {
	    queue.add(new Node(pair.getKey(), pair.getValue()));
	}
	return queue;
    }

    private Node buildHuffmanTree(Queue<Node> nodeQueue) {
	int initQSize = nodeQueue.size();
	for (int i = 1; i < initQSize; i++) {
	    Node node = new Node(null, 0);
	    node.left = nodeQueue.remove();
	    node.right = nodeQueue.remove();
	    node.incidence = node.left.incidence + node.right.incidence;
	    nodeQueue.add(node);
	}
	return nodeQueue.remove();
    }

    private void buildCharacterCodes(Node node, String code, Map<Character, String> charCodes) {
	if (node.left != null) {
	    buildCharacterCodes(node.left, code + "0", charCodes);
	    buildCharacterCodes(node.right, code + "1", charCodes);
	} else {
	    charCodes.put(node.character, code);
	}
    }

    private int buildCompressedFile(Map<Character, String> charCodes, char[] originFileChars, Node huffTree, String pathDir) throws IOException {
	String bitSequence = "";
	for (int i = 0; i < originFileChars.length; i++) {
	    bitSequence += charCodes.get(originFileChars[i]);
	}
	byte[] bytesToWrite = assembleBitSequenceIntoBytes(bitSequence);
	saveHuffmanTreeToFile(huffTree, pathDir + TREE_FILENAME);
	BufferedOutputStream fileOutput = new BufferedOutputStream(new FileOutputStream(new File(pathDir + COMPRESSED_FILENAME)));
	fileOutput.write(bytesToWrite);
	fileOutput.close();
	return bytesToWrite.length * 8;
    }

    private byte[] assembleBitSequenceIntoBytes(String bitSequence) {
	char[] bitSequenceArr = bitSequence.toCharArray();
	short trailingByte = (short) (bitSequenceArr.length % 8 > 0 ? 1 : 0);
	byte[] byteSequence = new byte[bitSequenceArr.length / 8 + trailingByte + 1];
	byteSequence[0] = (byte) (8 - bitSequenceArr.length % 8);
	byte nextByte;
	for (int i = 0; i < bitSequenceArr.length; i += 8) {
	    BitSet bit = new BitSet(8);
	    if (bitSequenceArr.length - i >= 8) {
		for (int n = 0; n < 8; n++) {
		    if (bitSequenceArr[i + n] == '1') {
			bit.set(n);
		    } else {
			bit.set(n, false);
		    }
		}
	    } else {
		for (int n = 0; n < (bitSequenceArr.length - i); n++) {
		    if (bitSequenceArr[i + n] == '1') {
			bit.set(n);
		    } else {
			bit.set(n, false);
		    }
		}
		for (int n = bitSequenceArr.length - i; n < 8; n++) {
		    bit.set(n, false);
		}
	    }
	    if (bit.toByteArray().length > 0) {
		nextByte = bit.toByteArray()[0];
	    } else {
		nextByte = 0;
	    }
	    byteSequence[i / 8 + 1] = nextByte;
	}
	return byteSequence;
    }

    private void saveHuffmanTreeToFile(Node huffTree, String filePath) throws IOException {
	List<Character> huffTreeInOrder = new ArrayList<Character>();
	saveHuffTreeInOrder(huffTree, huffTreeInOrder);
	BitSet fileBits = new BitSet();
	int bitInd = 0;
	for (int i = 0; i < huffTreeInOrder.size(); i++) {
	    if (huffTreeInOrder.get(i) == '0') {
		fileBits.set(bitInd, false);
	    } else {
		fileBits.set(bitInd, true);
		appendCharToBitSequence(huffTreeInOrder.get(i + 1), fileBits, ++bitInd);
		bitInd += 7;
		i++;
	    }
	    bitInd++;
	}
	BufferedOutputStream fileOutput = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
	fileOutput.write(fileBits.toByteArray());
	fileOutput.close();
    }

    private void saveHuffTreeInOrder(Node node, List<Character> output) {
	if (node.left != null) {
	    output.add('0');
	    saveHuffTreeInOrder(node.left, output);
	    saveHuffTreeInOrder(node.right, output);
	} else {
	    output.add('1');
	    output.add(node.character);
	}
    }

    private void appendCharToBitSequence(Character character, BitSet bitSequence, int bitInd) {
	char[] charTemp = {character};
	byte[] encodedChar = new String(charTemp).getBytes(StandardCharsets.ISO_8859_1);
	BitSet encodedCharBits = BitSet.valueOf(encodedChar);
	for (short i = 0; i < 8; i++) {
	    bitSequence.set(bitInd + i, encodedCharBits.get(i));
	}
    }

    private Node reconstructHuffmanTree(BitSet fileBits) {
	BitSet charBits;
	byte[] encodedChar;
	String charString;
	if (fileBits.get(readPos)) {
	    charBits = fileBits.get(readPos + 1, readPos + 9);
	    encodedChar = charBits.toByteArray();
	    charString = new String(encodedChar, StandardCharsets.ISO_8859_1);
	    readPos += 9;
	    return new Node(charString.charAt(0), 0);
	} else {
	    readPos++;
	    Node node = new Node(null, 0);
	    node.left = reconstructHuffmanTree(fileBits);
	    node.right = reconstructHuffmanTree(fileBits);
	    return node;
	}
    }

    private List<Character> decompFileWithHuffTree(String filePath, Node huffRoot) throws IOException {
	byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
	BitSet fileBits = BitSet.valueOf(fileBytes);
	Node currNode = huffRoot;
	List<Character> fileChars = new ArrayList<Character>();
	Byte firstByte = fileBytes[0];
	for (int i = 8; i < fileBits.length(); i++) {
	    if (fileBits.get(i)) {
		if (currNode.right.right != null) {
		    currNode = currNode.right;
		    continue;
		} else {
		    if (currNode.right != null) {
			fileChars.add(currNode.right.character);
		    } else {
			throw new IllegalArgumentException("Input file is not valid");
		    }
		}
	    } else {
		if (currNode.left.left != null) {
		    currNode = currNode.left;
		    continue;
		} else {
		    if (currNode.left != null) {
			fileChars.add(currNode.left.character);
		    } else {
			throw new IllegalArgumentException("Input file is not valid");
		    }
		}
	    }
	    currNode = huffRoot;
	}
	return fileChars;
    }

}
