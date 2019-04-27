//Corbyn Noble-May 1314639
//Liam Rodgers 1248912

import java.io.*;
import java.util.ArrayList;

public class LZencode {
	public static void main(String[] args){
		int maxBufferSize=256;
		int maxSize;
		TrieNode trie=new TrieNode(0);
		int input, buffer[]=new int[maxBufferSize];
		String[] pair = {null};
		try{
			if(args.length!=1) {
				throw new IOException();
			}
		maxSize=Integer.parseInt(args[0]);
		while((input=System.in.read())!=-1){
			for(int i=0;i<buffer.length;i++)
			    if(buffer[i]==0){
				buffer[i]=input;
				i=buffer.length;
			    }
			if((pair=trie.find(buffer).split(" ", 2)).length==2){
			    System.out.print(pair[0]);
			    System.out.println(pair[1]);
			    if(dropZeros(Integer.toBinaryString(trie.nextId)).length()>maxSize){
				System.out.println("\u0000");
				trie.reset();
			    }
			    buffer=new int[maxBufferSize];
			    pair=new String[0];
			}
		}
		if(pair.length>0){
			if(pair.length==1) {
			    pair=new String[]{pair[0], "\u0000"};
			}
			System.out.print(pair[0]);
			System.out.print(pair[1]);
		}
		}catch(IOException e){
		    System.err.println("Usage: java Encoder <Dictionary Size(bits)>");
		}catch(Exception e){
		    System.err.println("error:");
		    e.printStackTrace();
		}
	}
	private static String dropZeros(String str){
	while(str.charAt(0)=='0') {
		str=str.substring(1);
	}
	return str;
	}
}

class TrieNode{
    final int id;
    final int data;
    int nextId = 0;
    private ArrayList<TrieNode> children=new ArrayList<TrieNode>();
    
    public TrieNode(int incomingData){
	id = nextId;
	data = incomingData;
	nextId++;
    }

    public String find(int[] data){
	for(TrieNode t:children) {
	    if(t.data==data[0]){
		if(data[1]!=0){
		    int[] tail=new int[data.length-1];
		    System.arraycopy(data,1,tail,0,tail.length);
		    //System.out.println(tail.toString());
		    return t.find(tail);
		}else
		    return Integer.toString(t.id);
	    }
	}
	TrieNode t = new TrieNode(data[0]);
	children.add(t);
	return Integer.toString(id) +" "+ (char)data[0];
    }
	
	public void reset(){
		children=new ArrayList<TrieNode>();
		nextId=1;
	}
}
