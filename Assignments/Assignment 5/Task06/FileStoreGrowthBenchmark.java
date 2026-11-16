package Task06;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.*;
public class FileStoreGrowthBenchmark {
    static void make(Path p,int n)throws Exception{
        try(BufferedWriter w=Files.newBufferedWriter(p,StandardCharsets.UTF_8)){
            for(int i=0;i<n;i++)w.write(String.format(Locale.ROOT,"P%06d,REG%09d,Student%d,%d,%.2f%n",i,i,i,i%20+1,4500.0));
        }
    }
    public static void main(String[] args)throws Exception{
        for(int n:new int[]{1000,10000,100000}){
            Path p=Files.createTempFile("growth",".csv");make(p,n);
            PassStore s=new PassStore(p);long start=System.nanoTime();
            String[] found=s.find(String.format(Locale.ROOT,"P%06d",n-1));
            long elapsed=System.nanoTime()-start;
            System.out.printf("%d records: %.3f ms; found=%s%n",n,elapsed/1e6,found!=null);
            Files.deleteIfExists(p);
        }
    }
}
