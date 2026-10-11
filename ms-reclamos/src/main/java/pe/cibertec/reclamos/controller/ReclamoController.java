package pe.cibertec.reclamos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.reclamos.dto.ReclamoRequest;
import pe.cibertec.reclamos.model.Reclamo;
import pe.cibertec.reclamos.service.ReclamoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reclamos")
public class ReclamoController {

    private final ReclamoService reclamoService;

    public ReclamoController(ReclamoService reclamoService) {
        this.reclamoService = reclamoService;
    }

    @PostMapping
    public ResponseEntity<Reclamo> registrar(@RequestBody ReclamoRequest request) {
        Reclamo nuevoReclamo = reclamoService.registrarReclamo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReclamo);
    }

    @GetMapping
    public ResponseEntity<List<Reclamo>> listar() {
        return ResponseEntity.ok(reclamoService.listarReclamos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamo> buscarPorId(@PathVariable Integer id) {
        Optional<Reclamo> reclamo = reclamoService.buscarPorId(id);
        return reclamo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reclamo> actualizar(@PathVariable Integer id, @RequestBody Reclamo detallesReclamo) {
        Reclamo actualizado = reclamoService.actualizarReclamo(id, detallesReclamo);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }
}