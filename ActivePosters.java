package twitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.json.DataObjectFactory;

public class ActivePosters {
	public static void main(String args[]) throws FileNotFoundException, TwitterException{
	//int count=0;
	//System.out.println(count);
		/*Scanner b=new Scanner(new BufferedReader(new FileReader("filtered.json")));
		while(b.hasNextLine()){
		count++;
		}*/
		//System.out.println(count);
Scanner a=new Scanner(new BufferedReader(new FileReader("FilteredTweets.json")));
//System.out.println(count);
//String[] id_strings= new String[count+1];
String[] screen_name= new String[50000];
String[] name= new String[50000];
String[] sn= new String[50000];
String[] n= new String[50000];
long[] id1=new long[50000];
long[] l1=new long[50000];
int[] c_array=new int[50000];
for(int i=0;i<50000;i++){
	screen_name[i]=null;
	name[i]=null;
	sn[i]=null;
	n[i]=null;
	id1[i]=0;
	l1[i]=0;
	c_array[i]=1;
}
int index=0;
while(a.hasNextLine()){	
		String line=a.nextLine();
		//System.out.println(line);
	Status status=DataObjectFactory.createStatus(line);	
	sn[index]= status.getUser().getScreenName();
	//System.out.println(sn[index]);
	n[index]=status.getUser().getName();
    l1[index]=status.getId();
	index++;
	}
//System.out.println(index);
/*for(int i=0;i<index+1;i++){
	System.out.println(sn[i]);
	System.out.println(n[i]);
	System.out.println(l1[i]);
}*/
for(int i=0;i<index+1;i++){
	for(int j=i+1;j<index+1;j++){
		if(l1[i]==l1[j] && i!=j){
			c_array[i]=c_array[i]+1;
		}
	}
}
String s=null;
String n1=null;
int swap=0;
	for (int c = 0; c < index+1; c++) {
	    for (int d = 0; d < index+1 - c ; d++) {
	      if (c_array[d] > c_array[d+1]) 
	      {
	        s=sn[d];
	        sn[d]=sn[d+1];
	        sn[d+1]=s;
	        n1=n[d];
	        n[d]=n[d+1];
	        n[d+1]=n1;
	    	  swap       = c_array[d];
	        c_array[d]   = c_array[d+1];
	        c_array[d+1] = swap;
	      }
	    }
	  }	

System.out.println("The top 10 users are:");
for(int l=0;l<10;l++){
	System.out.println(sn[l]+":"+n[l]+":"+c_array[l]);
}

		
}

}
