import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import proto.Employee;

import java.io.FileWriter;
import java.io.IOException;

public class BenchMark {

    static FileWriter fileWriter = null;

    static {
        try {
            fileWriter = new FileWriter("/Users/kamalsharma/Desktop/resp_1000000.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){



        ObjectMapper mapper = new ObjectMapper();



        json.Employee empJ = new json.Employee();
                empJ.setName("Kamal Sharma");

                empJ.setEmpId("PAYTM-26847");
                empJ.setAddress("Pawan Vihar Colony , Pithoragarh, Uttarakhand");
                empJ.setEmail("kamal.sharma@paytm.com");
                empJ.setPhoneNo(9911729459L);

        proto.Employee empP = Employee.newBuilder().
                setName("Kamal Sharma")
                .setEmpId("PAYTM-26847")
                .setAddress("Pawan Vihar Colony , Pithoragarh, Uttarakhand")
                .setEmail("kamal.sharma@paytm.com")
                .setPhoneNo(9911729459L).build();

        Runnable json = () -> {

            try {
                byte[] bytes = mapper.writeValueAsBytes(empJ);
                json.Employee empD = mapper.readValue(bytes,json.Employee.class);
            } catch (Exception e) {
                System.out.println("Exception in JSON");
            }

        };

        Runnable proto = () -> {

            try {
                byte[] bytes = empP.toByteArray();
                proto.Employee sam1 = Employee.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                System.out.println("Exception in PROTO");
            }

        };

        for (int i = 0; i < 100; i++) {

            runPerformanceTest(json, "JSON");

            try {
                fileWriter.append("   |  ");
            } catch (IOException e) {
                e.printStackTrace();
            }

            runPerformanceTest(proto, "PROTO");

            try {
                fileWriter.append('\n');
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private static void runPerformanceTest(Runnable runnable, String method){
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            runnable.run();
        }
        long time2 = System.currentTimeMillis();

        System.out.println(
                method + " : " + (time2 - time1) + " ms"
        );

        writeToFile(""+(time2-time1));
    }

    private static void writeToFile(String line){

        try {

            fileWriter.append(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
