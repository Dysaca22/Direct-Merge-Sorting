# Direct Merge Sorting

Desarrollo de algoritmo en Java para la clase de Algoritmos en la Maestría de Ingeniería de Sistemas.
Estudio del tiempo de corrida y complejidad.

Se desarrollo un ejecutable encontrado en `exec` el cual permite primeramente seleccionar un archivo de entrada, posteriormente arroja como resultado un archivo de salida llamado `output.txt`.

_ _ _

## Pseudocódigo

```
Function directMerceSorting(entrada, n, temp_1, temp_2, salida) 
    Abrir entrada(E)
	Abrir salida(S)
	Lea entrada
	MQ !EOF(entrada)
		Escribir salida = entrada
		Lea entrada
	FinMQ
	Cerrar entrada
	Cerrar salida


	For i = 0; i < n; 1
		Abrir salida (E)
		Lea salida
		n_line = 1
		first_temp = true
		MQ !EOF(salida)
			Si (first_temp)
				temp = temp_1
			Sino
				temp = temp_2
			FinSi
			Abrir temp(S)
			Escribir temp = salida
            Cerrar temp
			
			Si (n_line % 2^i == 0)
				first_temp = !first_temp
			FinSi
			Lea salida
			n_line = n_line + 1
		FinMQ
        Cerrar salida

		n_pop_file_1 = 0
		n_pop_file_2 = 0
		Abrir temp_1(E)
		Abrir temp_2(E)
		Abrir salida(S)
		Lea temp_1
		Lea temp_2
        first_temp = true
        MQ !EOF(temp_1) || !EOF(temp_2)
            Si (n_pop_file_1 == 2^i && n_pop_file_2 == 2^i)
                n_pop_file_1 = 0
                n_pop_file_2 = 0
            FinSi

			Si (n_pop_file_1 == 2^i && n_pop_file_2 != 2^i)
				first_temp = false
			Si (n_pop_file_1 != 2^i && n_pop_file_2 == 2^i)
				first_temp = true
			Si (n_pop_file_1 != 2^i && n_pop_file_2 != 2^i && temp_1 != nulo &% temp_2 != nulo)
				first_temp = temp_1 < temp_2
			FinSi

            Si (first_temp)
                Escribir salida = temp_1
                Lea temp_1
                n_pop_file_1 = n_pop_file_1 + 1
            Sino
                Escribir salida = temp_2
                Lea temp_2
                n_pop_file_2 = n_pop_file_2 + 1
            FinSi
        FinMQ
        Cerrar temp_1
        Cerrar temp_2
        Cerrar salida

        Abrir temp_1
        Limpiar temp_1
        Cerrar temp_1
        Abrir temp_2
        Limpiar temp_2
        Cerrar temp_2
	FinFor
FinFuncion
```

_ _ _


## Cálculo del tiempo de corrida y complejidad


$$\begin{array}{cc}
T(n) &= 4 + \sum_{i = 1}^{n}{3} + 4 + \sum_{i = 1}^{\log_{2}{n}}{\left[6 + \sum_{j = 1}^{n}{\left(7 + \frac{1}{2^{i-1}} + 2\right)} + 10 + \sum_{j = 1}^{n}{\left(1 + \frac{1}{2^{i-1}} - \frac{2}{n} + 8\right)} + 10\right]}\\
  &= 4 + 3n + 4 + \sum_{i=1}^{\log_{2}{n}}{\left[26 + \sum_{j=1}^{n}{\left(18 + \frac{1}{2^{i-1}} + \frac{1}{2^{i-1}} - \frac{2}{n}\right)}\right]} \\
  &= 8 + 3n + \sum_{i=1}^{\log_{2}{n}}{\left[26 + 18n + n \cdot \left(\frac{1}{2^{i-2}} - 2\right)\right]} \\
  &= 8 + 3n + \log_{2}{n} \cdot \left(26 + 18n\right) + n\sum_{i=1}^{\log_{2}{n}}{\left(\frac{1}{2^{i-2}}\right)} - 2n\log_{2}{n} \\
  &= 8 + 3n + \log_{2}{n} \cdot \left(26 + 18n\right) + 4n\sum_{i=1}^{\log_{2}{n}}{\left(\frac{1}{2^{i}}\right)} - 2n\log_{2}{n} \\
  &= 8 + 3n + \log_{2}{n} \cdot \left(26 + 18n\right) + 4n\left[\sum_{i=0}^{\log_{2}{n}}{\left(\frac{1}{2^{i}}\right)} - 1 \right] - 2n\log_{2}{n}
\end{array}$$

Notamos que es una serie geometrica, entonces

$$\begin{array}{cc}
T(n) &= 8 + 3n + \log_{2}{n} \cdot \left(26 + 18n\right) + 4n\left(\frac{1 - \frac{1}{2^{\log_{2}{n} + 1}}}{1 - \frac{1}{2} - 1}\right) - 2n\log_{2}{n} \\
  &= 8 + 3n + \log_{2}{n} \cdot \left(26 + 18n\right) + 4n\left(1 - \frac{1}{n}\right) - 2n\log_{2}{n} \\
  &= 8 + 3n + \log_{2}{n} \cdot \left(26 + 18n\right) + 4n - 4 - 2n\log_{2}{n} \\
  &= 4 + 7n + 26 + 18n\log_{2}{n} - 2n\log_{2}{n} \\
  &= 4 + 7n + 26 + 16n\log_{2}{n}
\end{array}$$

Luego, la complejidad es: $\text{O}(n \ \log_{2}{n})$.
