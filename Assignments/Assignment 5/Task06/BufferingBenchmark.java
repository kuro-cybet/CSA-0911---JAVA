package Task06;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

public class BufferingBenchmark {
    static long count(InputStream in)throws IOException{
        long lines=0;int b;while((b=in.read())!=-1)if(b=='\n')lines++;return lines;
    }
    static void make(Path p,long targetBytes)throws IOException{
        byte[] row="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\n".getBytes(StandardCharsets.UTF_8);
        try(OutputStream out=Files.newOutputStream(p)){
            long written=0;while(written+row.length<=targetBytes){out.write(row);written+=row.length;}
            for(int i=0;written<targetBytes;i++,written++)out.write(row[i%row.length]);
        }
    }
    public static void main(String[] args)throws Exception{
        for(int mb:new int[]{1,10,50}){
            Path p=Files.createTempFile("attendance",".log");make(p,mb*1024L*1024L);
            long t=System.nanoTime(),a;
            try(InputStream in=Files.newInputStream(p)){a=count(in);}
            long unbuf=System.nanoTime()-t;
            t=System.nanoTime();long b;
            try(InputStream in=new BufferedInputStream(Files.newInputStream(p),65536)){b=count(in);}
            long buf=System.nanoTime()-t;
            System.out.printf("%d MiB (%d bytes): lines=%d/%d; unbuffered=%.3f ms; buffered=%.3f ms%n",
                    mb,Files.size(p),a,b,unbuf/1e6,buf/1e6);
            Files.deleteIfExists(p);
        }
    }
}
