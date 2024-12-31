package com.erich.dev.prod.dto.response;

import java.util.List;
import java.util.Map;

public record ProductResponsePag(List<ProductResponse> products, Map<String, Object> pages) {
}
