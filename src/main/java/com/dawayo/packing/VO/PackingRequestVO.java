package com.dawayo.packing.VO;


import java.util.List;


import lombok.Data;

@Data
public class PackingRequestVO {
    private List<PackingVO> scannedItems;
    private List<ScanErrorVO> scannedErrorItems;
}
