import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
// 刪除 URLDecoder

public class WordCounter {
	private String urlStr;
    private String content;
    
    public WordCounter(String urlStr){
    	this.urlStr = urlStr;
    }
    
    @SuppressWarnings("deprecation")
    private String fetchContent() throws IOException{
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
		String retVal = "";
	
		String line = null;
		
		while ((line = br.readLine()) != null){
		    retVal = retVal + line + "\n";
		}
	
		return retVal;
    }
    
    public int BoyerMoore(String T, String P){
//        int i = P.length() -1;
//        int j = P.length() -1;
        
        // Bonus: Implement Boyer-Moore Algorithm
        
        int n = T.length();
        int m = P.length();
        int count = 0;

        if (m == 0 || n < m) {
            return 0;
        }

        int i = 0;// i 為 Pattern 對齊 Text 的起始位置

        while (i <= n - m) {
            int j = m - 1;// j 為 Pattern 的最後一個字符的索引

            // 從 Pattern 尾端開始倒著比對
            while (j >= 0 && P.charAt(j) == T.charAt(i + j)) {
                j--;
            }
            
            if (j < 0) {
                // 完全匹配成功
                count++;
                // 往後移動，若下一個字元有在 Pattern 出現則依 lastIdx 移動，否則移 1 位
                i += (i + m < n) ? m - last(T.charAt(i + m), P) : 1;
            } else {
                // 發生不匹配，計算壞字元移動距離（確保至少移動 1 位）
                int lastIdx = last(T.charAt(i + j), P);
                i += Math.max(1, j - lastIdx);
            }
        }

        return count;// 將計數結果傳回，替換原本的 return -1
    }

    public int last(char c, String P){
    	// Bonus: Implement last occurence function
    	for (int i = P.length() - 1; i >= 0; i--) {
            if (P.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }

    public int min(int a, int b){
        if (a < b)
            return a;
        else if (b < a)
            return b;
        else 
            return a;
    }
    
    public int countKeyword(String keyword) throws IOException{
		if (content == null){
		    content = fetchContent();
		}
		
		//To do a case-insensitive search, we turn the whole content and keyword into upper-case:
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();
	
		int retVal = 0; 
		// 1. calculates appearances of keyword (Bonus: Implement Boyer-Moore Algorithm)
        retVal = BoyerMoore(content, keyword);
		return retVal;
    }
}
