package com.keysolutions.nacionalservice.database;

import com.keysolutions.nacionalservice.model.log.ConsultasReclamosDb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ManageNacionalData {
    @Autowired
    @Qualifier("digitalTemplate")
    private JdbcTemplate jdbcTemplate;

    public ConsultasReclamosDb retrieveConsultasReclamos(String phoneNumber){
        String sqlSelect="SELECT consulta_reclamo_id, id_proceso, id_rubro, id_ciudad, id_operador, servicio, fecha_contacto, "+
        " fecha_cierre_freshdesk, id_objecto, nombre_titular, nombre_solicitante, telefono, email, poliza, asunto, nombre_archivo, "+
        " status, record_date "+
                "	FROM deaxs_record.ns_consulta_reclamo cr "+
                "	where cr.telefono = ? LIMIT 1";
        ConsultasReclamosDb result = null;
        try {
            result = jdbcTemplate.queryForObject(sqlSelect, (rs, row) ->
                    new ConsultasReclamosDb(rs.getString("consulta_reclamo_id"),
                            rs.getString("id_proceso"),
                            rs.getString("id_rubro"),
                            rs.getString("id_ciudad"),
                            rs.getString("id_operador"),
                            rs.getString("servicio"),
                            rs.getString("fecha_contacto"),
                            rs.getString("fecha_cierre_freshdesk"),
                            rs.getString("id_objecto"),
                            rs.getString("nombre_titular"),
                            rs.getString("nombre_solicitante"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getString("poliza"),
                            rs.getString("asunto"),
                            rs.getString("consulta_reclamo_id"),//uniqueId
                            rs.getString("nombre_archivo"),
                            rs.getString("status"),
                            rs.getTimestamp("record_date")
                    ), new String[]{phoneNumber});
        } catch (Exception ex) {
            log.error("retrieveConsultasReclamos fail", ex);
        }
        return result;
    }

}
