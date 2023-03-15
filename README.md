Задание:
Напишите программу на языке программирования java, которая прочитает файл tickets.json и рассчитает:
- среднее время полета между городами Владивосток и Тель-Авив
- 90-й процентиль времени полета между городами  Владивосток и Тель-Авив

Программа должна вызываться из командной строки Linux, результаты должны быть представлены в текстовом виде.


Файл tickets.json находится в каталоге src/main/resources/ 
Процентиль расчитывается двумя методами:
1. P-й процентиль списка из N упорядоченных по величине чисел (от меньших к большим)
    является наименьшее в списке число, которое больше, чем N процентов всех чисел исследуемого ряда.
2. Для повышения точности расчета используется линейная интерполяция между соседними значениями в 
   наборе данных.
    
Для запуска приложения в коммандной строке введите:
1. Для windows - start.bat
2. Для Linux - bash start
