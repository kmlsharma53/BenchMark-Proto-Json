import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import proto.Employee;

public class BenchMark {

    public static void main(String[] args){

        json.Employee empJ = new json.Employee();
        empJ.setName("Kamal Sharma");
        empJ.setEmpId("PAYTM-26847");
        empJ.setAddress("Pawan Vihar Colony , Pithoragarh, Uttarakhand");
        empJ.setEmail("kamal.sharma@paytm.com");
        empJ.setPhoneNo(9911729459L);

        ObjectMapper mapper = new ObjectMapper();

        Runnable json = () -> {

            try {
                byte[] bytes = mapper.writeValueAsBytes(empJ);
                json.Employee empD = mapper.readValue(bytes,json.Employee.class);
            } catch (Exception e) {
                System.out.println("Exception in JSON");
            }

        };

        proto.Employee empP = Employee.newBuilder().
                setName("Kamal Sharma")
                .setEmpId("PAYTM-26847")
                .setAddress("Pawan Vihar Colony , Pithoragarh, Uttarakhand")
                .setEmail("kamal.sharma@paytm.com")
                .setPhoneNo(9911729459L).build();

        Runnable proto = () -> {

            try {
                byte[] bytes = empP.toByteArray();
                proto.Employee sam1 = Employee.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                System.out.println("Exception in PROTO");
            }

        };

        for (int i = 0; i < 100; i++) {

            //runPerformanceTest(json, "JSON");
            runPerformanceTest(proto, "PROTO");

        }

    }

    private static void runPerformanceTest(Runnable runnable, String method){
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 9000000; i++) {
            runnable.run();
        }
        long time2 = System.currentTimeMillis();

        System.out.println(
                method + " : " + (time2 - time1) + " ms"
        );
    }
}
