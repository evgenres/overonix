package BlockIoDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Data {
    public String network;
    public List<Address> addresses;
    public int page;
    public boolean has_more;
}
