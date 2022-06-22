package id.raf.springbootassignment.transactionengine.service;

import id.raf.springbootassignment.transactionengine.service.RestModel.Seat;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RestService {

    private final WebClient client;

    public RestService() {
        client = WebClient.create();
    }

    public Seat getSeat(int row, int number, String hallId) {
        try {
            String url = "http://localhost:8081/api/seat/detail?row="+row+"&number="+number+"&hallId="+hallId;
            Seat res = client.get().uri(url).retrieve().bodyToMono(Seat.class).block();
            if (res != null) {
                return res;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }
}
