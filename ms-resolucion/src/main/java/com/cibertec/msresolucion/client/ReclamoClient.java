package com.cibertec.msresolucion.client;

import com.cibertec.msresolucion.dto.ReclamoResponse;
import com.cibertec.msresolucion.dto.ReclamoUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-reclamos")
public interface ReclamoClient {

	@GetMapping("/api/reclamos/{numReclamo}")
	ReclamoResponse buscarPorNumero(@PathVariable("numReclamo") Long numReclamo);

	@PutMapping("/api/reclamos/{numReclamo}")
	ReclamoResponse actualizar(@PathVariable("numReclamo") Long numReclamo,
							   @RequestBody ReclamoUpdateRequest request);
}
