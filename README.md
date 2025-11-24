
# Taller 6 – Comparación de Métodos de Ordenación  
### *Estructura de Datos – Unidad 2*

---
#  Tabla de Contenidos
- [Descripción General del Proyecto](#descripción-general-del-proyecto)
- [Requisitos para Ejecutar el Programa](#requisitos-para-ejecutar-el-programa)
  - [Requisitos de Software](#-requisitos-de-software)
  - [Requisitos del Proyecto](#-requisitos-del-proyecto)
  - [Requisitos de Ejecución](#-requisitos-de-ejecución)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Metodología de Pruebas](#metodología-de-pruebas)
- [Resultados Completos por Dataset](#resultados-completos-por-dataset)
  - [Citas – 100 registros (Aleatorio)](#citas--100-registros-aleatorio)
  - [Citas – 100 registros (Casi Ordenado)](#citas--100-registros-casi-ordenado)
  - [Pacientes – 500 registros (Duplicados)](#pacientes--500-registros-duplicados)
  - [Inventario – 500 registros (Inverso)](#inventario--500-registros-inverso)
- [Matriz de Recomendación](#matriz-de-recomendación)
- [Conclusiones](#conclusiones)
- [Cómo Ejecutar el Proyecto](#cómo-ejecutar-el-proyecto)
- [Buenas Prácticas Implementadas](#--buenas-prácticas-implementadas)

---

##  Descripción General del Proyecto

Este proyecto implementa y compara tres algoritmos clásicos de ordenación en Java **Bubble Sort (con corte temprano), Selection Sort e Insertion Sort** aplicados sobre diferentes conjuntos de datos reales simulados: citas médicas, pacientes y productos de inventario.  

El objetivo es **medir, analizar y comparar** su rendimiento según el tamaño del dataset, su nivel de orden previo y la presencia de duplicados, siguiendo criterios rigurosos de instrumentación, como:

- Conteo de comparaciones  
- Conteo de intercambios (swaps)  
- Medición del tiempo (en nanosegundos)  
- Repetición de corridas y uso de mediana  
- Aislamiento del I/O para evitar sesgos  

---
#  Requisitos para Ejecutar el Programa

Para garantizar el correcto funcionamiento del proyecto y la ejecución precisa de los algoritmos de ordenación implementados, se requiere lo siguiente:

##  Requisitos de Software
- **Java Development Kit (JDK) 17 o superior**  
  Necesario para compilar y ejecutar el proyecto.

- **IDE recomendado (uno de los siguientes):**
    - IntelliJ IDEA Community Edition
    - Visual Studio Code con extensión *“Extension Pack for Java”*

- **Git (opcional pero recomendable)**  
  Para clonar repositorios y gestionar versiones.

---

## ️ Requisitos del Proyecto
- La carpeta **`datasets/`** debe ubicarse al mismo nivel que **`src/`**  
  Ejemplo:
```
  Taller6-EstructurasDatos/
  ├── datasets/
  └── src/
 ```

- Los archivos `.csv` deben mantenerse con sus nombres originales, ya que el programa los carga de forma directa.

- Se requiere acceso de lectura a la carpeta `datasets/`.

---

#  Estructura del Proyecto

La estructura del proyecto refleja una separación clara entre lógica de negocio, modelos, utilidades, algoritmos de ordenación y carga de datos:

```
Taller6-EstructurasDatos
├── datasets/
│   ├── citas_100.csv
│   ├── citas_100_casi_ordenadas.csv
│   ├── inventario_500_inverso.csv
│   └── pacientes_500.csv
│
└── src/
    └── ed/u2/
        ├── app/
        │   └── MainRunner.java
        │
        ├── datasets/
        │   ├── CsvDataLoader.java
        │   └── DatasetGenerator.java
        │
        ├── models/
        │   ├── Appointment.java
        │   ├── InventoryItem.java
        │   └── Patient.java
        │
        ├── sorting/
        │   ├── BenchmarkResult.java
        │   ├── BubbleSort.java
        │   ├── InsertionSort.java
        │   ├── SelectionSort.java
        │   ├── SortingAlgorithm.java
        │   ├── SortingUtils.java
        │   └── SortMetrics.java
        │
        └── utils/
            └── KeyExtractor.java
```

---

# Metodología de Pruebas

Cada dataset se ordenó utilizando los tres algoritmos, midiendo:

-  Número de comparaciones  
-  Número de swaps  
-  Tiempo de ejecución (mediana)  
- Sensibilidad al orden inicial  
-  Estabilidad  

Se siguió el estándar recomendado:

- **R = 10 repeticiones por caso**
- Se descartan las primeras 3 corridas por calentamiento del JIT
- El tiempo se mide exclusivamente sobre el ordenamiento (sin I/O)

---

#  Resultados Completos por Dataset

### **Citas – 100 registros (Aleatorio)**  
| Algoritmo | Comparaciones | Swaps | Tiempo (ns) |
|-----------|---------------|--------|-------------|
| BubbleSort | 4940 | 2398 | 19,957,800 |
| InsertionSort | 2493 | 2398 | 5,219,400 |
| SelectionSort | 4950 | 90 | 6,315,600 |

---

### **Citas – 100 registros (Casi Ordenado)**  
| Algoritmo | Comparaciones | Swaps | Tiempo (ns) |
|-----------|---------------|--------|-------------|
| BubbleSort | 3519 | 237 | 7,935,300 |
| InsertionSort | 336 | 237 | **434,500** |
| SelectionSort | 4950 | 5 | 5,269,700 |

---

### **Pacientes – 500 registros (Duplicados)**  
| Algoritmo | Comparaciones | Swaps | Tiempo (ns) |
|-----------|---------------|--------|-------------|
| BubbleSort | 109,525 | 41,812 | 5,100,200 |
| InsertionSort | 42,311 | 41,812 | **1,218,700** |
| SelectionSort | 124,750 | 327 | 2,824,400 |

---

### **Inventario – 500 registros (Inverso)**  
| Algoritmo | Comparaciones | Swaps | Tiempo (ns) |
|-----------|---------------|--------|-------------|
| BubbleSort | 124,750 | 124,750 | 3,019,100 |
| InsertionSort | 124,750 | 124,750 | 1,384,000 |
| SelectionSort | 124,750 | 250 | **981,400** |

---

#  Matriz de Recomendación

| Condición | Algoritmo recomendado | Justificación |
|----------|------------------------|---------------|
| Dataset casi ordenado | **Insertion Sort** | Aprovecha el orden previo; pocas comparaciones. |
| n ≤ 500 y datos aleatorios | **Insertion Sort** | Mitad de comparaciones que Bubble/Selection. |
| Se desea minimizar swaps | **Selection Sort** | Cantidad constante de swaps muy baja. |
| Muchos duplicados | **Insertion Sort** | Mantiene estabilidad y evita comparaciones innecesarias. |
| Dataset completamente inverso | **Selection Sort** | Requiere muy pocos swaps comparado con los otros métodos. |
| Análisis didáctico de sensibilidad al orden | **Bubble Sort** | Cambio drástico según el orden inicial. |

---

#  Conclusiones

- **Insertion Sort** resultó ser el algoritmo más eficiente en la mayoría de casos reales, especialmente en datasets pequeños, aleatorios, casi ordenados o con duplicados.  
- **Selection Sort** mantiene un número fijo de comparaciones, pero destaca por su mínimo número de intercambios, lo que lo hace útil cuando los swaps son costosos o cuando el dataset está completamente inverso.  
- **Bubble Sort**, aun con corte temprano, es el menos eficiente en general. Sin embargo, en casos casi ordenados ofrece una mejora marcada y es útil para análisis educativos.  
- La instrumentación implementada permitió obtener métricas confiables sin interferencias de entrada/salida, logrando una evaluación realista del comportamiento de cada algoritmo.

---

# Cómo Ejecutar el Proyecto

1. Abrir el proyecto en **IntelliJ IDEA** o **VSCode con soporte para Java**  
2. Asegurarse de que la carpeta `datasets/` esté al mismo nivel que `src/`  
3. Ejecutar:

```
src/ed/u2/app/MainRunner.java
```

4. Los resultados aparecerán en consola en formato de tabla y también como archivo .csv al mismo nivel que las carpetas **datasets/** y **src/**.

---

#   ️ Buenas Prácticas Implementadas

- Código limpio, modular y fácilmente extensible  
- Separación clara de responsabilidades  
- Instrumentación detallada y validada  
- Evitación de I/O dentro de la medición  
- Tests con repetición y descarte de calentamiento  
- Uso de mediana para evitar valores atípicos

---
