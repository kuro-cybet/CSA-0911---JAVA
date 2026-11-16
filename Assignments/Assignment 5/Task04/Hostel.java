package Task04;
import java.io.*;
import java.util.*;

public class Hostel implements Serializable {
    @Serial private static final long serialVersionUID=1L;
    private List<Room> rooms=new ArrayList<>();
    public List<Room> getRooms(){return rooms;}
    public static class Room implements Serializable {
        @Serial private static final long serialVersionUID=1L;
        String roomNo; List<Resident> residents=new ArrayList<>();
        Room(String roomNo){this.roomNo=roomNo;}
    }
    public static class Resident implements Serializable {
        @Serial private static final long serialVersionUID=1L;
        String name,regNo,phone;
        transient String password;
        Resident(String name,String regNo,String phone,String password){
            this.name=name;this.regNo=regNo;this.phone=phone;this.password=password;
        }
    }
    public static void main(String[] args)throws Exception{
        Hostel h=new Hostel();Room r=new Room("A-101");
        r.residents.add(new Resident("Arun","192311001","9000000000","do-not-serialize"));
        h.rooms.add(r);
        PathHolder.save(h,"hostel.ser");
        Hostel loaded=PathHolder.load("hostel.ser");
        Resident resident=loaded.rooms.get(0).residents.get(0);
        System.out.println("Resident loaded: "+resident.name+" / "+resident.regNo);
        System.out.println("Password after deserialization: "+resident.password);
        new java.io.File("hostel.ser").delete();
    }
    static class PathHolder {
        static void save(Hostel h,String p)throws Exception{try(ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream(p))){o.writeObject(h);}}
        static Hostel load(String p)throws Exception{try(ObjectInputStream i=new ObjectInputStream(new FileInputStream(p))){return (Hostel)i.readObject();}}
    }
}
