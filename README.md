#### transaction_service
NTT Data Transaction Microservice Proyect

## Consideraciones
### Rutas / Endpoints activos
- Get: http://localhost:8095/api/v1/transactions
- Post: http://localhost:8095/api/v1/transactions
- Put: http://localhost:8095/api/v1/transactions
- GetById: http://localhost:8095/api/v1/transactions/bc389db14b04bda51fdf479b
- Delete: http://localhost:8095/api/v1/transactions/{bc389db14b04bda51fdf479b
### Config Server
- Personal Config Server Git: https://github.com/BenZumaran/NTTExternalConfigServer.git
- Personal Config File Git: https://github.com/BenZumaran/NTTDataServerConfigFiles.git
#### Archivos
- Ubicación archivo para MongoDB : {project.basedir}/db_data.json
- Ubicación archivo para post: {project.basedir}/input-example.json
- Ubicación archivo draw.io: {project.basedir}/SystemDiagrams.drawio
### Extras
- PORT: server.port=8083
- DATABASE: spring.data.mongodb.uri=mongodb://localhost:27017/NTTBankingService