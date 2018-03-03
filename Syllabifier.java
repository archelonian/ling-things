/*
 * Filename: Syllabifier.java TODO: better name?
 * Author: archelonian@github
 * Date of creation: 3/1/2018
 * Description: Partitions strings over {C, V}* into acceptable syllables based
 *              on a supplied list of permissible syllable formats (e.g. CV,
 *              CVC, CCVC).
 */

import java.util.* ;
import java.io.* ;

public class Syllabifier{
   public static final int EXPECTED_ARGS = 2 ;

   // words to partition
   private ArrayList<String> words ;
   // permitted syllable patterns TODO: select better data structure? consider
   //    using a hash map or stream (???) instead of ArrayList
   private ArrayList<String> patterns ;

   // the key is the word and the value is all found partitions for the word
   private HashMap<String, ArrayList<String>> partitions ;

   // constructor
   public Syllabifier(String wordsPath, String patternsPath)
      throws FileNotFoundException{

      // read from file
      Scanner scanWords = new Scanner(new File(wordsPath)) ;
      Scanner scanPatterns = new Scanner(new File(patternsPath)) ;

      String newWord ;
      String newPattern ;

      // populate instance variables
      words = new ArrayList<String>() ;
      patterns = new ArrayList<String>() ;
      partitions = new HashMap<String, ArrayList<String>>() ;

      while(scanWords.hasNextLine()){
         newWord = scanWords.nextLine() ;

         words.add(newWord) ;

         // have an empty list ready for each word
         ArrayList<String> emptyList = new ArrayList<String>() ;
         partitions.put(newWord, emptyList) ;
      }

      while(scanPatterns.hasNextLine()){
         newPattern = scanPatterns.nextLine() ;

         patterns.add(newPattern) ;
      }
   } // end constructor

   // partition goes through all the words and divides them into acceptable
   //    syllable combinations
   private void partition(){
      // inspect each word
      for(String word : words){
         // get syllable splits, start by checking from the first character and
         //    the accumulator starts empty
         if(word.length() > 1){
            checkSyll(word, word, "") ;
         }
         else {
            // only one character, add to partitions
            if(patterns.contains(word)){
               partitions.get(word).add(word) ;
            }
         }
      }
   } // end partition

   // TODO: debug code, delete later
   private void printList(ArrayList<String> list){
      for(int i = 0; i < list.size(); i++){
         System.out.println(list.get(i)) ;
      }
   }

   // checkSyll recursively checks if it is able to complete a valid syllable
   //    and move on. The goal here is to recurse on the first character, then
   //    the first two characters, and so on until no matching patterns can be
   //    found and that path is deemed impossible to further partition.
   //
   // segment is the part to recurse over and acc is previously processed valid
   //    syllables in the word. original is included to index into partitions
   private void checkSyll(String original, String segment, String acc){
      String currUnit ;
      String newAcc = "" ;

      // from first character only to entire segment
      for(int prefix = 1; prefix <= segment.length(); prefix++){
         currUnit = segment.substring(0, prefix) ;

         if(patterns.contains(currUnit)){
            // this is a valid syllable, put into newAcc as a syllable found in
            //    this iteration only
            newAcc = currUnit ;

            // recurse as long as currUnit is not the entire segment
            if(prefix < segment.length()){
               // add a boundary because there will be more characters
               newAcc += "|" ;

               // recurse on the rest of the segment, combine top level
               //    accumulator with newly found syllable
               checkSyll(original, segment.substring(prefix), acc + newAcc) ;
            }
            else {
               // if currUnit is the entire segment and it is a valid syllable,
               //    partition found
               partitions.get(original).add(acc + currUnit) ;
            }
         }
      } // end for loop
   } // end checkSyll

   // perform program output: write to console, no need to write to a file
   private void output(){
      ArrayList<String> currPartitions ;
      String currWord ;

      // remind reader what the syllables were
      System.out.print("Permitted syllables: ") ;

      for(int i = 0; i < patterns.size(); i++){
         System.out.print(patterns.get(i) + " ") ;
      }

      // spacing
      System.out.println() ;

      // iterate over the words in sequence
      for(int i = 0; i < words.size(); i++){
         // spacing
         System.out.println() ;

         currWord = words.get(i) ;

         System.out.println("Printing partitions for " + currWord) ;
         // get the list of all the partitions associated with this word
         currPartitions = partitions.get(currWord) ;

         if(currPartitions.isEmpty()){
            System.out.println("   No valid partitions found for " + currWord) ;
         }

         // print values in sequence
         for(int j = 0; j < currPartitions.size(); j++){
            System.out.println("   " + currPartitions.get(j)) ;
         }
      }
   } // end output

   // main method, creates a Syllabifier object and runs its methods
   public static void main(String[] args) throws FileNotFoundException{
      if(args.length != EXPECTED_ARGS){
         System.out.println("usage: java Syllabifier [words filepath] "
                            + "[syllables filepath]") ;
         System.out.println("\nwords and syllables files should have one word "
                            + "or syllable per line") ;
         return ;
      }

      // construct a Syllabifier and initialize its variables
      Syllabifier sy = new Syllabifier(args[0], args[1]) ;

      sy.partition() ;
      sy.output() ;

   } // end main

} // end Syllabifier
