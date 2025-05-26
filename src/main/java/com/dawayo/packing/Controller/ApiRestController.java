package com.dawayo.packing.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.ast.Or;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dawayo.packing.Service.OrderService;
import com.dawayo.packing.VO.PackingRequestVO;
import com.dawayo.packing.VO.PackingVO;
import com.dawayo.packing.VO.ScanErrorVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;
 
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiRestController {
    private final OrderService orderService;
    private final String consumer_key="ck_b2f69874352c6c35c49dff10d254a36986a2cc26";
    private final String consumer_secret="cs_e36d3ff7a86398ceb6885cac27b921c6b6707ce7";

@PostMapping("/getOrderDetail")
@ResponseBody
public ResponseEntity<String> getOrderDetail(@RequestParam("orderNumber") String orderNumber) throws IOException, InterruptedException {
    String productId = ""; // Replace with the actual product ID you want to retrieve
    String urlString = "https://dawayo.de/wp-json/wc/v3/orders/" + orderNumber +
            "?consumer_key=" + consumer_key +
            "&consumer_secret=" + consumer_secret;
    
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(urlString))
            .GET()
            .build();

    // orderNumber에 해당하는 주문 정보 가져오기        
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> map = objectMapper.readValue(response.body(), new TypeReference<Map<String, Object>>() {});
    System.err.println(map);
    List<Map<String, Object>> lineItems = (List<Map<String, Object>>) map.get("line_items");
   
    
    //getting product SKU from product meta data
    ArrayNode resultArray = objectMapper.createArrayNode();

    for (Map<String, Object> item : lineItems) {

        String name = (String) item.get("name");
        int quantity = (Integer) item.get("quantity");
        productId = Integer.toString((Integer) item.get("product_id"));

        String productUrlString = "https://dawayo.de/wp-json/wc/v3/products/" + productId +
                "?consumer_key=" + consumer_key +
                "&consumer_secret=" + consumer_secret;

        HttpRequest productRequest = HttpRequest.newBuilder()
                .uri(URI.create(productUrlString))
                .GET()
                .build();

        HttpResponse<String> productResponse = client.send(productRequest, HttpResponse.BodyHandlers.ofString());
        Map<String, Object> productMap = objectMapper.readValue(productResponse.body(), new TypeReference<Map<String, Object>>() {});
        List<Map<String, Object>> productMetaList = (List<Map<String, Object>>) productMap.get("meta_data");

        String sku = null;
        for (Map<String, Object> meta : productMetaList) {
            if ("custom_product_sku".equals(meta.get("key"))) {
                sku = (String) meta.get("value");
                System.out.println("SKU: " + sku);
                break;
            }
        }

        // JSON 객체 생성
        ObjectNode itemNode = objectMapper.createObjectNode();
        itemNode.put("orderNumber", orderNumber);
        itemNode.put("name", name);
        itemNode.put("quantity", quantity);
        itemNode.put("sku", sku != null ? sku : "");

        
        resultArray.add(itemNode);
    }


    String resultJson = objectMapper.writeValueAsString(resultArray);
    System.out.println("Final JSON Result: " + resultJson);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultJson);
}
    // @PostMapping("/saveScannedItems")
    // public ResponseEntity<String> saveScannedItems(PackingVO packingVO) {
    //     System.err.println("Received scanned items: " + packingVO.toString());
    //     // Save the scanned items to the database
    //     //orderService.save(packingVO);

    //     // Return a response
    //     return ResponseEntity.ok("Scanned items received successfully");
    // }
    @PostMapping("/saveScannedItems")
public ResponseEntity<String> saveScannedItems(@RequestBody PackingRequestVO request) {

    List<PackingVO> scannedItems = request.getScannedItems();
    List<ScanErrorVO> scannedErrorItems = request.getScannedErrorItems();

    System.out.println("✅ 스캔된 정상 아이템 수: " + scannedItems);
    System.out.println("⚠️ 스캔 오류 아이템 수: " + scannedErrorItems);

   
    for (PackingVO item : scannedItems) {
        System.err.println("Received item: " + item.toString());
    }
    //save the scanned items to the database
    orderService.saveAll(scannedItems);
    //save the scanned error items to the database
    orderService.saveAllError(scannedErrorItems);
    return ResponseEntity.ok("Scanned items received successfully");
}

}