liam Rodgers 1248912
Corbyn noble-may 1314639

import java.util.Scanner;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Base64;

class decoder
{
    //private static int index = 1; 
    private static List<Byte> mismatch = new ArrayList<Byte>();
    private static List<Integer> phraseNumber = new ArrayList<Integer>();
    private static List<Byte> toPrint = new ArrayList<Byte>();
    //private static ByteArrayOutputStream stream = new ByteArrayOutputStream();

    public static void main(String []args)
    {
        Scanner scanner = new Scanner(System.in);
        try
        {
            reset();

	    int value = 0;
            int phrase = 0;
            
            while(scanner.hasNext())
            {
                String line = scanner.nextLine();
                String []  ExtractedData = line.split(",");

		//if the extracted data is both a phrase and value then assign variables, 
		//if it was the last item then process it otherwise add to the lists and then process 
                if(ExtractedData.length == 2)
                {
                    phrase = Integer.parseInt(ExtractedData[0]);
                    value = Integer.parseInt(ExtractedData[1]);

                    if(!scanner.hasNext() && value == 0)
                    {
                        process(phrase);
                    }
                    else
                    {
                        phraseNumber.add(phrase);
                        mismatch.add((byte) value);

                        process(phrase);

                        toPrint.add((byte) value);
                    }
                }
                
                else
                {
                    line = line + ", 0";
                    ExtractedData = line.split(","); 

		    //does this ever happen?
                    if(ExtractedData[0].equals("\u0000"))
                    {
                        System.err.println("RESET");
                        reset();
                    }
                }
            }


            byte [] byteArray = new byte[toPrint.size()];
            for(int x = 0; x < toPrint.size(); x++)
            {
                    byteArray[x] = toPrint.get(x);
            }

            System.out.write(byteArray);

        }
        catch(Exception e)
        {
            System.err.println("Error : " + e);
            e.printStackTrace();
        }

    }

    //recursivly prints mismatches using the given phrase number
    private static void process(int t)
    {
        int temp = t;
        List<Byte> printList = new ArrayList<Byte>();

        while (temp != 0)
        {
            printList.add(mismatch.get(temp));
            temp = phraseNumber.get(temp);
        }
	
	//loop through list
        for (int x = printList.size(); x > 0; x--)
        {
            toPrint.add(printList.get(x - 1));
        }
    }

    // clear phrases and start again
    private static void reset()
    {
        mismatch.clear(); //clear list of mismatches
        mismatch.add((byte)0); //add default 0
        
        phraseNumber.clear(); //clear list of phrase numbers
        phraseNumber.add(0); //add default 0

        index = 1;
    }
}