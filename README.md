# beer_club-back
Este repositorio contiene una aplicación back-end desarrollada en Java, utilizando el framework [Spring Boot]. La aplicación se conecta con una base de datos MySQL para gestionar y persistir datos.
# Creación de la estructura basica
En esta rama se encuentra la estructura basica propuesta para nuestro proyecto
# Documentación del Proyecto

Bienvenido al repositorio del proyecto. Aquí encontrarás las normas y convenciones que seguimos durante el desarrollo.

## Estrategia de Ramas

Para mantener un flujo de trabajo claro y organizado, utilizamos una estrategia basada en historias de usuario. Por cada historia, creamos una rama con el siguiente formato:

Por ejemplo, para la historia número 12, la rama se llamará `feature/BCA_12`.
```bash
    feature/<BCA_numero_de_la_historia>
```

## Convenciones de Código

### CamelCase

Utilizamos la convención CamelCase para nombrar clases, métodos y variables. Esto garantiza una consistencia y legibilidad en nuestro código.

Ejemplo:

```java
public class UserInfo {
    private String userName;
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
```

### Idioma en el Código y en las Respuestas de las APIs
* **Código**: Todos los nombres de clases, métodos y variables se escriben en inglés.

* **Respuestas API**: Aunque el código esté en inglés, las respuestas de nuestras APIs se proporcionan en castellano para facilitar la interacción con los consumidores de la API que hablen este idioma.

### Test Unitarios
Es fundamental que todos los servicios tengan test unitarios asociados. Esto nos ayuda a garantizar que el código funcione como se espera y facilita la detección temprana de errores.

### Respuestas de las API
Para que las respuestas de nuestras APIs sean claras y consistentes, seguimos estas directrices:

* **Uso de Códigos de Estado HTTP**: Las respuestas de las APIs se acompañan de códigos de estado HTTP apropiados. Esto ayuda a los consumidores de la API a entender rápidamente el resultado de su solicitud.

* **Respuestas Detalladas**: Además del código de estado, proporcionamos mensajes descriptivos en castellano que detallan el resultado de la solicitud.

### Recomendaciones Adicionales
* **Documentación**: Es fundamental mantener la documentación actualizada. Si introduces un cambio importante en el funcionamiento de alguna funcionalidad, asegúrate de reflejarlo en la documentación.

* **Revisiones de Código**: Antes de fusionar cualquier rama con main, es crucial que otro miembro del equipo revise el código. Esto garantiza que el código mantenga un estándar de calidad y permite la detección temprana de posibles errores.

### Diagrama de la base de datos
De igual forma se deja el `.png` de este dentro del proyecto

![Diagrama de la base de datos](/Esquema.png)
