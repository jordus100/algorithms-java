package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LongestCommonSubsequenceTest {
    LongestCommonSubsequence longestCommSubseq;

    private void testFindLCS(String str1, String str2, String expected) {
        longestCommSubseq = new LongestCommonSubsequence(str1, str2);
        assertEquals(expected, longestCommSubseq.findLCS());
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_constructor_argument_is_null() {
        longestCommSubseq = new LongestCommonSubsequence("test", null);
    }

    @Test
    public void test_findLCS_for_standard_cases() {
        testFindLCS("TEST1", "TEST2", "TEST");
        testFindLCS("abcd", "EFGH", "");
        testFindLCS("ABCD", "BCDE", "BCD");
        testFindLCS("ABCDEFG", "ACF", "ACF");
    }

    @Test
    public void test_findLCS_for_special_characters_strings() {
        testFindLCS("test 1", "test 2", "test ");
        testFindLCS("ab\ncd", "ab\nfg", "ab\n");
        testFindLCS("AB\tCD", "B\tCDE", "B\tCD");
        testFindLCS("AB\rCDEFG", "A\rEG", "A\rEG");
        testFindLCS("łakamakafą" , "fąfel", "fą");
    }

    @Test
    public void test_findLCS_for_empty_strings(){
        testFindLCS("", "", "");
    }

    @Test
    public void test_display_of_lcs_matrix_without_calling_findLCS() {
        longestCommSubseq = new LongestCommonSubsequence("ala mąkota", "ala\nmakota");
        longestCommSubseq.display();
    }

    @Test
    public void test_display_of_lcs_matrix_after_calling_findLCS() {
        longestCommSubseq = new LongestCommonSubsequence("OIOIOIOIOIOIOOOIOIOIOIOIOOIOI", "OIOIOIOIOIOIOIOIOIOIOIOOIOIO");
        longestCommSubseq.findLCS();
        longestCommSubseq.display();
    }

    @Test
    public void test_display_for_empty_strings() {
        longestCommSubseq = new LongestCommonSubsequence("", "");
        longestCommSubseq.display();
    }

}
