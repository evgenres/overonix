import BlockIoDto.BlockIoResponseWallets;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class BlockIoTest {

    @Test
    public void getWallets() throws IOException {
        FileWriter csvWriter;

        BlockIoResponseWallets wallets = given()
                .baseUri("https://block.io/")
                .basePath("api/v2/get_my_addresses/")
                .formParam("api_key", "af54-470b-fd70-5df1")
                .param("page", 1)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .extract()
                .body()
                .as(BlockIoResponseWallets.class);

        var currency = wallets.getData().getNetwork();
        var addresses = wallets.getData().getAddresses();

        var allWalets = "allWallets.csv";
        csvWriter = new FileWriter(allWalets);

        var walletsAdress = addresses.stream().map(x -> x.getAddress()).collect(Collectors.toList());
        var walletsBalance = addresses.stream().map(x -> x.getAvailable_balance()).collect(Collectors.toList());

        csvWriter.append("currency, address, balance \n");
        for (int i = 0; i < addresses.size(); i++) {

            csvWriter.append(currency).append(",")
                    .append(walletsAdress.get(i))
                    .append(",")
                    .append(walletsBalance.get(i))
                    .append("\n");

        }
        csvWriter.flush();
        csvWriter.close();

        var walletsWithBalance = "walletsWithBalance.csv";

        csvWriter = new FileWriter(walletsWithBalance);

        var adressWalletsWithBalance = addresses.stream()
                .filter(x -> Double.parseDouble(x.getAvailable_balance()) > 0)
                .map(x -> x.getAddress())
                .collect(Collectors.toList());

        var positiveBalance = addresses.stream()
                .filter(x -> Double.parseDouble(x.getAvailable_balance()) > 0)
                .map(x -> x.getAvailable_balance())
                .collect(Collectors.toList());

        csvWriter.append("currency, address, balance \n");
        for (int i = 0; i < positiveBalance.size(); i++) {

            csvWriter.append(currency).append(",")
                    .append(adressWalletsWithBalance.get(i))
                    .append(",").append(positiveBalance.get(i))
                    .append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }
}