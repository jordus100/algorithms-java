package pl.edu.pw.ee;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class HuffmanTest {

    private final String ORIGIN_FILENAME = "org.txt";
    private final String DECOMPRESSED_FILENAME = "decompressed.txt";
    private Huffman huffman;

    @Before
    public void setUp() {
	huffman = new Huffman();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldDetectNullArgumet() {
	huffman.huffman(null, true);
    }

    @Test
    public void testHuffmanCompress() throws Exception {
	int numOfOutputBits = huffman.huffman("", true);
	org.junit.Assert.assertTrue(numOfOutputBits > 0);
    }

    @Test
    public void testHuffmanDecompress() throws Exception {
	int numOfCompChars = huffman.huffman("", false);
	assertEquals(3101, numOfCompChars);
	byte[] f1 = Files.readAllBytes(Paths.get(ORIGIN_FILENAME));
	byte[] f2 = Files.readAllBytes(Paths.get(DECOMPRESSED_FILENAME));
	org.junit.Assert.assertArrayEquals(f1, f2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongInputFile() throws Exception {
	huffman.huffman("bla", true);
    }

}
