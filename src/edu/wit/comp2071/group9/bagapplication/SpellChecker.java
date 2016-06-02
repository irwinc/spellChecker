
/**
 * A class that utilized a bag class to compare words between a given file and a dictionary
 @author Connor Irwin
 @author Max Agress
 @author Zack Arnold
 @version 1.0
 */
package edu.wit.comp2071.group9.bagapplication;
import java.io.*;


public class SpellChecker
{

    private BagInterface<Object> dictionaryBag = null; //dictionaryBag constructor
    private BagInterface<Object> fileBag = null; //fileBag constructor

    /**
     * adds dictionary file to bag
     * @return dictionaryBag
     */
    public void dictionaryToBag()
    {
        String  dictionary = "american-english-JL.txt"; //define dictionary file
        String line;
        try {
            FileReader fileReader = new FileReader(dictionary);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) //reads file line-by-line, word-by-word
            {
                if (!dictionaryBag.add(line.toLowerCase())) //if ArrayBag class is used check to see if array is full
                {
                    Object[] temp = dictionaryBag.toArray();
                    ArrayBag<Object> tempBag = new ArrayBag<>((dictionaryBag.getCurrentSize() * 2)); //expand array
                    for (int j = 0; j < dictionaryBag.getCurrentSize(); j++)
                    {
                        tempBag.add(temp[j]);
                    }
                    dictionaryBag = tempBag;
                }
            }
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Dictionary file not found"); //if dictionary file not found
            System.exit(1);
        }
        catch(IOException ex)
        {
            System.out.println("Error reading file");
        }
    }


    /**
     * adds specified file to bag
     * @return fileBag
     */
    public void fileToBag(String fileName)
    {
        String line;
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null)
            {

                if (line.isEmpty())
                {
                    continue;
                }
                Object[] words = line.replaceAll("-", " ").replaceAll("(http.*\\s|http.*)", "").replaceAll("(www.*\\s|www.*)", "")
                        .replaceAll("(https.*\\s|https.*)", "").replaceAll("[^a-zA-Z/ ]", "").replaceAll("[/]", " ").toLowerCase()
                        .replaceAll("(txt)", " ").replaceAll("(doc)", " ").replaceAll("(docx)", " ").replaceAll("(pdf)", " ")
                        .replaceAll("(ppt)", " ").replaceAll("(pptx)", " ").replaceAll("(odt)", " ").replaceAll("(odf)", " ")
                        .replaceAll("(mp3)", " ").replaceAll("(mp4)", " ").replaceAll("(avi)", " ").split("\\s+");
                //parces out non alpha characters, url address, and some file extensions
                for (int i = 0; i < words.length; i++)
                {

                    if (!fileBag.add(words[i])) //if ArrayBag class is used check to see if array is full
                    {
                        Object[] temp = fileBag.toArray();
                        ArrayBag<Object> tempBag = new ArrayBag<>((fileBag.getCurrentSize()*2)); //expand array
                        for (int j = 0; j < fileBag.getCurrentSize(); j++)
                        {
                            tempBag.add(temp[j]);
                        }
                        fileBag = tempBag;
                    }
                }
            }

        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Dictionary file not found");
            System.exit(1);
        }
        catch(IOException ex)
        {
            System.out.println("Error reading file");
        }
    }

    /**
     * compares fileBag to dictionaryBag for mismatching words and outputs the mismatching words
     * @param fileName
     */
    public void check(String fileName)
    {
        dictionaryBag = new ArrayBag<>(); //constructs dictionary
        dictionaryToBag();
        fileBag = new LinkedBag<>(); //constructs BagInterface
        fileToBag(fileName); //sends specified file to bag
        Object[] fileArray = fileBag.toArray(); //sends bag to array
        System.out.println("Incorrectly spelled words in " + fileName + ":");

        for (int i = 0; i < fileBag.getCurrentSize(); i++) //prints array of incorrectly spelled words
        {
            if (!dictionaryBag.contains(fileArray[i]))
            {
                System.out.println(fileArray[i]);
            }
        }
    }

    /**
     * Main method that passes the check method a specified file name
     * @param args
     */
    public static void main(String[] args)
    {
        SpellChecker s = new SpellChecker(); //constructs SpellChecker
        s.check("wit-attendance-policy"); //spell checks file
        System.out.println();
        s.check("the-lancashire-cotton-famine"); //spell checks file
        System.out.println();
        s.check("sources");
    }
}
