
# Mario Game with DevOps Pipeline

This project is a comprehensive DevOps demonstration that showcases the development, containerization, deployment, and monitoring of a Mario game application. The project leverages modern technologies and tools to build a robust, scalable, and maintainable system.

**How it works ?**

*Development:* The project starts with developing the Mario game using React for the frontend and Spring Boot for the backend, connected to a MySQL database.

*Containerization:* Each component is containerized using Docker, creating separate images for the frontend, backend, and database.

*Deployment:* The containerized application is deployed on a Minikube Kubernetes cluster, utilizing Kubernetes resources like pods, deployments, and services to manage the application.

*Monitoring:* The ELK stack is set up to monitor the application's logs and performance metrics. Filebeat is used to ship logs to Logstash, which processes and sends them to Elasticsearch. Kibana provides a dashboard for visualizing the data.



## Tech Stack

**Client:** [React](https://react.dev/blog/2023/03/16/introducing-react-dev), [Kaboom.js](https://kaboomjs.com/), npm

**Server:** [Spring Boot](https://spring.io/projects/spring-boot) , Maven, [MYSQL](https://www.mysql.com/)

**DevOps Tools:** [Jenkins](https://www.jenkins.io/), [Docker](https://www.docker.com/), [Minikube](https://minikube.sigs.k8s.io/docs/start/?arch=%2Fwindows%2Fx86-64%2Fstable%2F.exe+download), [Elasticsearch](https://www.elastic.co/elasticsearch), [Logstash](https://www.elastic.co/logstash), [Kibana](https://www.elastic.co/kibana), [Filebeat](https://www.elastic.co/beats/filebeat)

