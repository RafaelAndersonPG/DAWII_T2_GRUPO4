package pe.cibertec.reclamos.service;

import org.springframework.stereotype.Service;
import pe.cibertec.reclamos.model.Reclamo;
import pe.cibertec.reclamos.repository.ReclamoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReclamoService {


    private final ReclamoRepository reclamoRepository;


    public ReclamoService(ReclamoRepository reclamoRepository) {
        this.reclamoRepository = reclamoRepository;
    }

    public Reclamo registrarReclamo(Reclamo reclamo) {
        return reclamoRepository.save(reclamo);
    }

    public List<Reclamo> listarReclamos() {
        return reclamoRepository.findAll();
    }

    public Optional<Reclamo> buscarPorId(Integer numreclamo) {
        return reclamoRepository.findById(numreclamo);
    }

    public Reclamo actualizarReclamo(Integer numreclamo, Reclamo detallesReclamo) {
        Optional<Reclamo> reclamoOpt = reclamoRepository.findById(numreclamo);
        if (reclamoOpt.isPresent()) {
            Reclamo reclamo = reclamoOpt.get();
            reclamo.setNombreAnalista(detallesReclamo.getNombreAnalista());
            reclamo.setEstado(detallesReclamo.getEstado());
            reclamo.setObservacion(detallesReclamo.getObservacion());
            if (detallesReclamo.getMotivo() != null) {
                reclamo.setMotivo(detallesReclamo.getMotivo());
            }
            return reclamoRepository.save(reclamo);
        }
        return null;
    }
}