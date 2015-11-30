import java.util.Arrays;

public class CountingStars {
  public static void main(String[] args){
    String[] words1 = {"twinkle", "little", "star"};
    String[] words2 = {"one", "two","three" };
    String[] words3 = {"twinkle", "little", "star", "how", "wonder", "what", "you", "are"};
    System.out.println(Arrays.toString(count_star(words1)));
    System.out.println(Arrays.toString(count_star(words2)));
    System.out.println(Arrays.toString(count_star(words3)));
  }

  public static String[] count_star(String[] words){
    String[] new_words = new String[words.length];
    int count = 0;
    int length = 0;
    for(String word : words){

      if(word.equals("star")){
        count = count + 1 ;
      }
      else{
        new_words[length] = word;
      }
      length++;
    }
    if((count & 1) == 0){
      return words;
    }
    else {
      return new_words;
    }
  }
}
