# JavaDB - In-Memory Database

Welcome to **JavaDB**, a simple in-memory database application written in Java. This application allows users to create tables, insert data, and query information from those tables.



## Example Usage

```sql
JavaDB> CREATE TABLE users (id INT, firstName VARCHAR, lastName VARCHAR);
Table 'users' created.

JavaDB> INSERT INTO users VALUES (null, 'Ahmed', 'Abbas');
Row inserted into table 'users'.

JavaDB> INSERT INTO users VALUES (null, 'Chakib', 'Samali');
Row inserted into table 'users'.

JavaDB> SELECT * FROM users;
Data from table 'users':
{id=1, firstName=Ahmed, lastName=Abbas}
{id=2, firstName=Chakib, lastName=Samali}

JavaDB> UPDATE users SET lastName = 'BATAL' WHERE firstName = 'Ahmed';
Row updated in table 'users'.

JavaDB> SELECT * FROM users;
Data from table 'users':
{id=1, firstName=Ahmed, lastName=BATAL}
{id=2, firstName=Chakib, lastName=Samali}

JavaDB> DELETE FROM users WHERE firstName = 'Chakib';
Row deleted from table 'users'.

JavaDB> SELECT * FROM users;
Data from table 'users':
{id=1, firstName=Ahmed, lastName=BATAL}
```