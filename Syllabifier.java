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
         // reset found partitions
         partitions.clear() ;

         // get syllable splits, start by checking from the first character and
         //    the accumulator starts empty
         if(word.length() > 1){
            checkSyll(word.substring(0, 1), word.substring(1), "") ;
         }
         else {
            // only one character, rest of word is of length 0
            checkSyll(word, "", "") ;
         }

         // add all found partitions to output list
         outputs.put(word, partitions) ;
      }
   } // end partition

   // checkSyll recursively checks if it is able to complete a valid syllable
   //    and move on. The goal here is to recurse on the base itself, then the
   //    base and one more character, then two, until no matching pattern can
   //    be found and that path is deemed impossible to further partition.
   private void checkSyll(String base, String rest, String acc){
      // assume the word is partitionable to start with
      boolean possible = true ;

      // how many characters in addition to the base to start with?
      int prefixSize = 0 ;
      String currUnit = base ;

      while(possible && prefixSize < rest.length()){
         if(rest.length() == 0){
            // only base remains, don't bother recursing, just check
            if(patterns.contains(currUnit)){
               acc += base ;

               partitions.add(acc) ;
            }

            return ;
         }
         else {
            // current potential syllable to check for
            currUnit = base + rest.substring(prefixSize) ;

            if(patterns.contains(currUnit)){
               // got a permitted syllable, add it to the accumulator
               acc += (base + "|") ;
      
               // found syllable recorded, partition the rest
               if(rest.length() > 1){
                  checkSyll(rest.substring(0, 1), rest.substring(1), acc) ;
               }
               else {
                  // only one character, remaining is of length 0
                  checkSyll(rest, "", "") ;
               }
            }
            else {
               // no longer possible to continue partitioning from here
               possible = false ;
            }
         }

         // check on an additional character next time
         prefixSize++ ;
      }

      // final catch all
      return ;

   } // end checkSyll

   // perform program output: write to file
   private void output(){
      // TODO write out to file
   } // end output

   // main methods, creates a Syllabifier object and runs its methods
   public static void main(String[] args){
      Syllabifier sy = new Syllabifier() ;

      sy.partition() ;
      sy.output() ;

   } // end main

} // end Syllabifier
