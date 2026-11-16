package Task01;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/** Full seven-field bus-pass text store. Uses pipe delimiter with backslash escaping. */
public class BusPassFileDesign {
    public record Pass(String passId,String regNo,String name,int route,String stop,
                       String validity,double amount) {}
    private final Path file;
    public BusPassFileDesign(Path file){this.file=file;}

    private static String esc(String s){return s.replace("\\","\\\\").replace("|","\\|").replace("\n","\\n").replace("\r","\\r");}
    private static List<String> parse(String line) throws IOException {
        List<String> fields=new ArrayList<>(); StringBuilder b=new StringBuilder(); boolean slash=false;
        for(char c:line.toCharArray()){
            if(slash){ if(c=='n') b.append('\n'); else if(c=='r') b.append('\r'); else b.append(c); slash=false; }
            else if(c=='\\') slash=true;
            else if(c=='|'){fields.add(b.toString());b.setLength(0);}
            else b.append(c);
        }
        if(slash) throw new IOException("Trailing escape");
        fields.add(b.toString()); return fields;
    }
    public void add(Pass p)throws IOException{
        Path parent=file.toAbsolutePath().getParent(); if(parent!=null)Files.createDirectories(parent);
        try(BufferedWriter w=Files.newBufferedWriter(file,StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,StandardOpenOption.APPEND)){
            w.write(String.join("|",esc(p.passId()),esc(p.regNo()),esc(p.name()),
                    Integer.toString(p.route()),esc(p.stop()),esc(p.validity()),Double.toString(p.amount())));
            w.newLine();
        }
    }
    public Pass find(String id)throws IOException{
        if(!Files.exists(file))return null;
        try(BufferedReader r=Files.newBufferedReader(file,StandardCharsets.UTF_8)){
            String line;
            while((line=r.readLine())!=null){
                try{
                    List<String> f=parse(line); if(f.size()!=7)continue;
                    Pass p=new Pass(f.get(0),f.get(1),f.get(2),Integer.parseInt(f.get(3)),
                            f.get(4),f.get(5),Double.parseDouble(f.get(6)));
                    if(p.passId().equals(id))return p;
                }catch(RuntimeException|IOException damaged){ /* skip malformed record */ }
            }
        }
        return null;
    }
    public List<Pass> listByRoute(int route)throws IOException{
        List<Pass> result=new ArrayList<>(); if(!Files.exists(file))return result;
        try(BufferedReader r=Files.newBufferedReader(file,StandardCharsets.UTF_8)){
            String line;
            while((line=r.readLine())!=null)try{
                List<String> f=parse(line); if(f.size()!=7)continue;
                Pass p=new Pass(f.get(0),f.get(1),f.get(2),Integer.parseInt(f.get(3)),
                        f.get(4),f.get(5),Double.parseDouble(f.get(6)));
                if(p.route()==route)result.add(p);
            }catch(RuntimeException|IOException ignored){}
        }
        return result;
    }
    public static void main(String[] args)throws Exception{
        Path p=Files.createTempFile("bus-passes",".txt"); Files.delete(p);
        BusPassFileDesign store=new BusPassFileDesign(p);
        store.add(new Pass("BP01","192311001","Arun|Kumar",12,"Main Gate","2027-03-31",4500));
        store.add(new Pass("BP02","192311002","Divya",7,"Library","2027-03-31",3800));
        System.out.println("Find BP01: "+store.find("BP01"));
        System.out.println("Route 7: "+store.listByRoute(7).size()+" pass(es)");
        System.out.println("Delimiter in name preserved: "+store.find("BP01").name());
        Files.deleteIfExists(p);
    }
}
