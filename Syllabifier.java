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
   // permitted syllable patterns TODO: select better data structure?
   private ArrayList<String> patterns ;

   // constructor
   public Syllabifier(){
      // TODO: read/write from/to file

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
   }

   // partition goes through all the words and divides them into acceptable
   //    syllable combinations
   private void partition(){
      // inspect each word
      for(String word : words){
         // TODO
      }
   }

   // perform program output: write to file
   private void output(){
      // TODO write out to file
   }

   // main methods, creates a Syllabifier object and runs its methods
   public static void main(String[] args){
      Syllabifier sy = new Syllabifier() ;

      sy.partition() ;

      sy.output() ;

   } // end main

} // end Syllabifier
