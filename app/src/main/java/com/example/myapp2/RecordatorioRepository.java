package com.example.myapp2;

public class RecordatorioRepository {
        private final RecordatorioDataSource datasource;
        public RecordatorioRepository(final RecordatorioDataSource datasource) {
            this.datasource = datasource;
        }
        public void recuperarRecordatorios(RecordatorioDataSource.RecuperarRecordatorioCallback callback){
            datasource.recuperarRecordatorios(callback);
        }
        public void guardarRecordatorio(RecordatorioModel recordatorio, RecordatorioDataSource.GuardarRecordatorioCallback callback){
            datasource.guardarRecordatorio(recordatorio,callback);
        }

}
