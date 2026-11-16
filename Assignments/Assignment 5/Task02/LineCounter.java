package Task02;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LineCounter {
    public static long countUnbuffered(Path p)throws IOException{
        long lines=0; boolean any=false,lastNewline=false;
        try(InputStream in=Files.newInputStream(p)){
            int b; while((b=in.read())!=-1){any=true;if(b=='\n')lines++;lastNewline=(b=='\n');}
        }
        return any&&!lastNewline?lines+1:lines;
    }
    public static long countBuffered(Path p)throws IOException{
        long lines=0; boolean any=false,lastNewline=false;
        try(BufferedInputStream in=new BufferedInputStream(Files.newInputStream(p),64*1024)){
            byte[] buf=new byte[64*1024]; int n;
            while((n=in.read(buf))!=-1){for(int i=0;i<n;i++){any=true;if(buf[i]=='\n')lines++;lastNewline=buf[i]=='\n';}}
        }
        return any&&!lastNewline?lines+1:lines;
    }
    public static void main(String[] args)throws Exception{
        Path p=Files.createTempFile("attendance",".log");
        Files.writeString(p,"A\nB\nC\nD");
        long a=countUnbuffered(p),b=countBuffered(p);
        System.out.println("Unbuffered lines: "+a);
        System.out.println("Buffered lines: "+b);
        Files.deleteIfExists(p);
    }
}
