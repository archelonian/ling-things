/*
 * Filename: Syllabifier.java TODO: better name?
 * Author: archelonian@github
 * Date of creation: 3/1/2018
 * Description: Partitions a string over {C, V}* into acceptable syllables based
 *              on a supplied list of permissible syllable formats (e.g. CV,
 *              CVC, CCVC).
 */

// disclaimer: yup I switched to java. actually I should probably switch again
//             to javascript, but parsing java to javascript should either be
//             easy or something to practice with.

import java.util.* ;

public class Syllabifier{
   // words to partition
   private ArrayList<String> words ;
   // permitted syllable patterns TODO: select better data structure? consider
   //    using a hash map or stream instead of ArrayList
   private ArrayList<String> patterns ;

   // accumulate partitions of current word
   private ArrayList<String> partitions ;

   // the key is the word and the value is all found partitions for the word
   //    (e.g. "CV|CV") 
   private HashMap<String, ArrayList<String>> outputs ;

   // constructor
   public Syllabifier(){
      // TODO: read from file, do not permit empty strings

      // dummy values to test with
      String dummyWords[] = {"CVC", "CVCCVCVC", "CVCVCCCVVC", "CVCV", "CVCVC"} ;
      String dummyPatterns[] = {"CV", "CVC"} ;

      // populate instance variables
      words = new ArrayList<String>() ;
      patterns = new ArrayList<String>() ;
      partitions = new ArrayList<String>() ;
      outputs = new HashMap<String, ArrayList<String>>() ;

      for(String word : dummyWords){
         words.add(word) ;
      }

      for(String pattern : dummyPatterns){
         patterns.add(pattern) ;
      }
   } // end constructor

   // partition goes through all the words and divides them into acceptable
   //    syllable combinations
   private void partition(){
      // inspect each word
      for(String word : words){
         // TODO: debug code, delete later
         System.out.println("in word: " + word) ;

         // reset found partitions
         partitions.clear() ;

         // get syllable splits, start by checking from the first character and
         //    the accumulator starts empty
         if(word.length() > 1){
            checkSyll(word, "") ;
         }
         else {
            // only one character, rest of word is of length 0
            if(patterns.contains(word)){
               partitions.add(word) ;
            }
         }

         // TODO: debug code, delete later
         System.out.println("adding this set of partitions to word " + word) ;
         printList(partitions) ;
         // add all found partitions to output list
         outputs.put(word, partitions) ;
      }
   } // end partition

   // TODO: debug code, delete later
   private void printList(ArrayList<String> list){
      for(int i = 0; i < list.size(); i++){
         System.out.println(list.get(i)) ;
      }
   }

   // checkSyll recursively checks if it is able to complete a valid syllable
   //    and move on. The goal here is to recurse on the base itself, then the
   //    base and one more character, then two, until no matching pattern can
   //    be found and that path is deemed impossible to further partition.
   private void checkSyll(String word, String acc){
      String currUnit ;

      // from first character only to entire word
      for(int prefix = 1; prefix <= word.length(); prefix++){
         currUnit = word.substring(0, prefix) ;

         if(patterns.contains(currUnit)){
            // this is a valid syllable, add to acc
            acc += currUnit ;

            // recurse as long as currUnit is not the entire word
            if(prefix < word.length()){
               // add a boundary because there will be more characters
               acc += "|" ;

               // recurse on the rest of the word
               checkSyll(word.substring(prefix), acc) ;
            }
            else {
               // if currUnit the entire word and it is valid, partition found
               partitions.add(acc) ;
            }
         }
      }
   } // end checkSyll

   // perform program output: write to file
   private void output(){
      // TODO write out to file

      ArrayList<String> currPartitions ;
      String currWord ;

      // iterate over the words in sequence
      for(int i = 0; i < words.size(); i++){
         // spacing
         System.out.println() ;

         currWord = words.get(i) ;

         System.out.println("Printing partitions for " + currWord) ;
         // get an array of all the partitions associated with this word
         currPartitions = outputs.get(currWord) ;

         if(currPartitions.size() == 0){
            System.out.println("\tNo valid partitions found for word "
                               + currWord) ;
         }

         // print values in sequence
         for(int j = 0; j < currPartitions.size(); j++){
            System.out.println("\t" + currPartitions.get(j)) ;
         }
      }
   } // end output

   // main methods, creates a Syllabifier object and runs its methods
   public static void main(String[] args){
      Syllabifier sy = new Syllabifier() ;

      sy.partition() ;
      sy.output() ;

   } // end main

} // end Syllabifier
