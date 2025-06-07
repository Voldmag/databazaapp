---
В данном файле подробно расписаны все моменты в разработке кода, представленного по пути src/main/java/databazaapp
---

### **Main.java:**
1. **Switch case:**
    - Существует 2 разных синтаксиса Switch case:
        1. `case 1 -> название метода`
        2. `case 2:`
                `название метода ();`
                `break;`
    - Оба они выполняют одинаковую функцию, разница лишь в синтаксисе.

2. **do - while:**
    - Конструкция, которая выполняет цикл с пост условием т.е. сначала выполняется 1 итерация цикла, потом проверяется условие. Если условие соблюдается, цикл проходит ещё одну итерацию, если не проходит - возвращается. Наиболее ярко это отражено в мотоде `addTask`:
    `private static void addTask() {`
        `System.out.println("\nДобавление новой задачи:");`
        `String title;`
        `do {`
            `title = readLine("Введите название задачи (не пустое): ");`
           ` if (title.isBlank()) {`
                `System.out.println("Название не может быть пустым.");`
            `}`
        `} while (title.isBlank());`
    - На этом примере видно, что изначально, после требования о вводе, объявляется новая текстовая переменная title (название), в которую мы принимаем значение из сканнера строки , если оно пустое: показывается на экране сообщение "название не может быть пустым" и запускается проверка цикла, title пустое (), или же нет, если пустое, то запускает новую итерацию цикла.
    - Это же продемонстрировано в методе `updateTask`:
    `do {`
            `status = readLine("Введите статус (NEW, IN_PROGRESS, DONE): ").toUpperCase();`
            `if (!status.equals("NEW") && !status.equals("IN_PROGRESS") && !status.equals("DONE")) {`
                `System.out.println("Некорректный статус. Попробуйте снова.");`
            `}`
        `} while (!status.equals("NEW") && !status.equals("IN_PROGRESS") && !status.equals("DONE"));`
    - Мы объявляем переменную status, требуем ввести, и запускаем очередную проверку, если значение статуса не равно "NEW" ИЛИ(&&) "IN_PROGRESS" ИЛИ "DONE", то выбивает ошибка (некорректный статус) и после проверки цикла на пригодность запускается следующая итерация.
 
3. **return:**
    - Оператор, который  прекращает и возвращает данные. Если говорить простым языком, он возвращает переменные (допустим в return Integer.parseInt(input) return возвращает значение числа и завершает работу программы. **Важно заметить**, то, что возвращает программа, прописана в методе, метод `private static int readInt (string prompt)` будет возвращать переменную readInt, если в методе прописано void, значит метод ничего не возвращает, а просто закрывает метод)

### **DatabaseHandler:**

1. **return:**
    - Оператор, который  прекращает и возвращает данные. Если говорить простым языком, он возвращает переменные, хорошо это можно увидеть в примере:
    `public boolean deleteTask(int id) {`
        `String sql = "DELETE FROM tasks WHERE id = ?";`
        `try (Connection conn = getConnection();`
            ` PreparedStatement stmt = conn.prepareStatement(sql)) {`
            `stmt.setInt(1, id);`
            `int rowsDeleted = stmt.executeUpdate();`
            `return rowsDeleted > 0;`
        `} catch (SQLException e) {`
        `    System.err.println("Ошибка при удалении задачи: " + e.getMessage());`
        `    return false;`
        `}`
    `}`
    - В данном случае мы пытаемся путём ошибки try-catch понять, удалено больше 0 строк, или же нет. В блоке `try` если количество удалённых строк (rowsDeleted) больше 0, то выдаётся значение true и мы возвращаем в файл `Main.java` `deleteTask` как `true`, если мы ловим любую ошибку SQL` (SQLexception e)`, то `deleteTask` принимает значени `false` и передаётся дальше как `false`. В файле Main.java мы перепринимаем, объявляем и перезаписываем переменную `deleted` в пользу читаемости текста. Если `deleted` = `true`, то значит задача удалена успешно, если `deleted` = `false`, то значит произошла ошибка удаления. По такой же аналогии работает и прочие вариации кода.

2. **stmt и PreparedStatement**
    - PreparedStatement - функция интеграции sql в java, которая выполняет функцию объявления запроса. До этого мы объявляем переменную sql и даём ей данные нашего будущего запроса. Если у нас есть connection (мы смогли подключиться к базе данных), то мы вводим запрос. Если мы не установили подключение, то запрос будет гарантированно завершен.
    - stmt - это переменная, представляющая объект PreparedStatement. Возьмём пример из кода и объясним, что в нём делает stmt
    `public boolean updateTask(Task task) {`
        `String sql = "UPDATE tasks SET title = ?, description = ?, status = ? WHERE id = ?";`
        `try (Connection conn = getConnection();`
             `PreparedStatement stmt = conn.prepareStatement(sql)) {`
            ``
            `stmt.setString(1, task.getTitle());`
            `stmt.setString(2, task.getDescription());`
            `stmt.setString(3, task.getStatus());`
            `stmt.setInt(4, task.getId());`
            ``
            `int rowsUpdated = stmt.executeUpdate();`
            `return rowsUpdated > 0;`
            ``
        `} catch (SQLException e) {`
        `    System.err.println("Ошибка при обновлении задачи: " + e.getMessage());`
            `return false;`
        `}`
    `}`
    - В данном примере stmt устанавливает значение первого заполнителя (первое ?) в SQL запросе равным title, которое мы получаем из task. Аналогично делается для 2, 3 и 4 заполнителя.