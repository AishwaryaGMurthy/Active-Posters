package twitter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.Scanner;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.json.DataObjectFactory;

public class PostActivity {

	public static void main(String[] args) throws FileNotFoundException, TwitterException {
		long[] l1=new long[50000];
		long[] t1=new long[50000];
		int[] c_array=new int[50000];
		for(int i=0;i<50000;i++){
			l1[i]=0;
			t1[i]=0;
			c_array[i]=1;
		}
Date date=new Date();
Scanner a=new Scanner(new BufferedReader(new FileReader("filtered.json")));
int index=0;
while(a.hasNextLine()){	
	String line=a.nextLine();
	//System.out.println(line);
Status status=DataObjectFactory.createStatus(line);	
l1[index]=status.getId();
date=status.getCreatedAt();
t1[index]=date.getTime();
index++;
}
for(int i=0;i<index+1;i++){
	for(int j=i+1;j<index+1;j++){
	if((t1[j]-t1[i])<=10){
		c_array[j]=c_array[j]+1;
		System.out.println(t1[j]+","+c_array[j]);
	}
	}
	}
	}
}
