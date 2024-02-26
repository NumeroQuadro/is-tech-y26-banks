# Цель

Изучение синтаксиса языка Java и нового окружения

# Отрабатываемый материал

Применить на практике принципы из SOLID, GRASP, паттерны GoF.

# Задание

- В рамках лабораторной следует переписать лабораторную работу №4 из прошлого семестра (формулировка чуть ниже)
- Необходимо использовать [Javadoc](https://www.baeldung.com/javadoc) и сгенерировать html-документацию.

# Формулировка

Есть несколько **Банков**, которые предоставляют финансовые услуги по операциям с деньгами.

В банке есть **Счета** и **Клиенты**. У клиента есть имя, фамилия, адрес и номер паспорта (имя и фамилия обязательны, остальное – опционально).

### Счета

Каждый счет принадлежит какому-то клиенту. Счета бывают трёх видов:

### **Дебетовый счет**

- фиксированный процент на остаток
- Деньги можно снимать в любой момент
- В минус уходить нельзя
- Комиссий нет

### **Депозитный счет**

- нельзя снимать и переводить деньги до тех пор, пока не закончится его срок (пополнять можно).
- Процент на остаток зависит от изначальной суммы, **Пример:**
    - депозит до 50 000 р. - 3%
    - от 50 000 р. до 100 000 р. - 3.5%
    - больше 100 000 р. - 4%.
- Комиссий нет
- Проценты должны задаваться для каждого банка свои

### **Кредитный счет**

- имеет кредитный лимит, в рамках которого можно уходить в минус (в плюс тоже можно).
- Процента на остаток нет.
- Есть фиксированная комиссия за использование, если клиент в минусе.

### Процент на остаток

Периодически банки проводят операции по выплате процентов и вычету комиссии. 

- Начисляется ежедневно от текущей суммы в этот день, но выплачивается раз в месяц (и для дебетовой карты и для депозита).

Это значит, что **нужен механизм ускорения времени**, чтобы посмотреть, что будет через день/месяц/год и т.п.

**Пример:** дебетовый счёт с процентом 3.65% годовых. Значит в день прибавка к суммарному проценту составит 3.65% / 365 дней = 0.01% от суммы на счету. 

1. Допустим, у клиента сегодня на счету 100 000 р. - за первый день пользования счётом к суммарному проценту прибавилось 10 рублей. 
2. На второй день ему пришла ЗП и стало 200 000 р. — за этот день ему добавили ещё 20 рублей.
3. На третий день он купил себе новый ПК и у него осталось 50 000 р. - добавили 5 рублей. 

Таким образом, к концу месяца складываем все, что запоминали. Допустим, вышло 300 р. - эта сумма добавляется к счету или депозиту в текущем месяце.

Разные банки предлагают разные условия. В каждом банке известны величины процентов и комиссий.

### Центральный банк

- занимается регистрацией всех банков (предоставлять возможность создать банк)
- предоставляет необходимый функционал, чтобы банки могли взаимодействовать с другими банками (например, можно реализовать переводы между банками через него).
- уведомляет другие банки о том, что нужно начислять остаток или комиссию - для этого механизма не требуется создавать таймеры и завязываться на реальное время.

### Операции и транзакции

Счета должны предоставлять следующие механизмы:

- снятие денег со счёта
- пополнение счёта
- перевод денег между счетами (то есть счетам нужны некоторые идентификаторы)
- также обязателен механизм **отмены транзакций:**
    - Если вдруг выяснится, что транзакция была совершена злоумышленником, то такая транзакция должна быть отменена.
    - Отмена транзакции подразумевает возвращение банком суммы обратно.
    - Транзакция не может быть повторно отменена.
- Счета должны хранить в себе историю совершённых над ними транзакций.

### Создание клиента и счета

Клиент должен создаваться по шагам: 

- Сначала он указывает **имя** и **фамилию** (обязательно)
- Затем указывается **адрес** (можно пропустить и не указывать)
- Затем указываются **паспортные данные** (можно пропустить и не указывать).

Если при создании счета у клиента не указаны адрес или номер паспорта, мы объявляем такой счет (любого типа) **сомнительным**, и запрещаем операции снятия и перевода выше определенной суммы (у каждого банка своё значение). 

Если в дальнейшем клиент указывает всю необходимую информацию о себе - счет перестает быть сомнительным и может использоваться без ограничений.

### Обновление условий счетов

Банки должны предоставлять возможность:

- изменения процентов
- изменения лимитов не перевод
- возможность пользователям подписываться на информацию о таких изменениях (банк должен предоставлять возможность клиенту подписаться на уведомления)
    
    Пример: изменение лимита для кредитных карт - все пользователи, которые подписались и имеют кредитные карты, должны получить уведомление.
    

> Стоит продумать расширяемую систему, в которой могут появится разные способы получения нотификаций клиентом (да, да, это референс на тот самый сайт).
> 

### Консольный интерфейс работы

Для взаимодействия с банком требуется реализовать консольный интерфейс:

- который будет взаимодействовать с логикой приложения
- отправлять и получать данные
- отображать нужную информацию
- предоставлять интерфейс для ввода информации пользователем.

### Дополнения

- На усмотрение студента можно ввести свои дополнительные идентификаторы для пользователей, банков etc.
- На усмотрение студента можно пользователю добавить номер телефона или другие характеристики, если есть понимание зачем это нужно.

### QnA

**Question:** Нужно ли предоставлять механизм отписки от информации об изменениях в условии счетов 

**Answer:** Не оговорено, значит на ваше усмотрение (это вообще не критичный момент судя по условию лабы)

**Question:** Транзакциями считаются все действия со счётом, или только переводы между счетами. Если 1, то как-то странно поддерживать отмену операции снятия, а то после отмены деньги удвоятся: они будут и у злоумышленника на руках и на счету. Или просто на это забить 

**Answer:** Все операции со счетами - транзакции.

**Question:** Фиксированная комиссия за использование кредитного счёта, когда тот в минусе измеряется в % или рублях, и когда её начислять: после выполнения транзакции, или до. И нужно ли при отмене транзакции убирать и начисленную за неё комиссию. 

**Answer:** Фиксированная комиссия означает, что это фиксированная сумма, а не процент. Да, при отмене транзакции стоит учитывать то, что могла быть также комиссия.

**Question:** 

Ситуация: 

1. Перевод денег со счета 1 на счёт 2
2. Перевод денег со счёта 2 на счёт 3

Если транзакция подразумевает возвращение суммы обратно, но при этом эта же сумма была переведена на несколько счетов. Что происходит если клиент 1 отменяет транзакцию? 

Подразумевается ли что деньги по цепочке снимаются со счёта 3? (на счету 2 их уже физически нет) 

Или у нас банк мошеннический, и деньги "отмываются" и возмещаются клиенту 1 с уводом счёта 2 в минус?

**Answer:** Банк не мошеннический, просто упрощённая система. Транзакции не связываются между собой. Так что да, можно считать, что может уйти в минус.

Фреймворк для тестирования рекомендуется[JUnit](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api).

Система сборки предоставляется на выбор студента: Gradle/Maven.

В отличии от последующих, эта лабораторная заливается отдельным проектом
