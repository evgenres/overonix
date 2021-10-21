package BlockIoDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    public int user_id;
    public String address;
    public String label;
    public String pending_received_balance;
    public String available_balance;
    public boolean is_segwit;
}
