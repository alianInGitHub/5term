# XML
Задание: 

Создать файл XML и соответствующую ему схему XSD(Обязательно должно быть несколько xsd и пространства имен). 
При разработке XSD использовать простые и комплексные типы, перечисления, шаблоны и предельные значения, обязательно использование атрибутов и типа ID.
Сгенерировать (создать) Java-класс, соответствующий данному описанию. 
Создать Java-приложение для разбора XML-документа и инициализации коллекции объектов информацией из XML-файла. Для разбора использовать SAX, DOM и StAX парсеры. Для сортировки объектов использовать интерфейс Comparator.
Произвести проверку XML-документа с привлечением XSD. 
Определить метод, производящий преобразование разработанного XML-документа в документ, указанный в каждом задании.

Вариант:
Конфеты.
Name – название конфеты.
Energy– калорийность (ккал).
Type (должно быть несколько) – тип конфеты (карамель, ирис, шоколадная [с начинкой либо нет]).
Ingredients (должно быть несколько) – ингредиенты: вода, сахар (в мг), фруктоза (в мг), тип шоколада (для шоколадных), ванилин (в мг)
Value – пищевая ценность: белки (в гр.), жиры (в гр.) и углеводы (в гр.).
Production – предприятие-изготовитель.
Корневой элемент назвать Candy.