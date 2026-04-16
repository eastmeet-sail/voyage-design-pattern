# Iterator 패턴

## 개념

```java
for (int i = 1; i <= 5; i++) {
    System.out.println("i = " + i);
}
```
- i는 구체적인 값으로 i++에 의해 순차적으로 1만큼 증가한다.
- 이러한 구체적인 변수 i의 기능을 추상화하여 일반화한 것을 디자인 패턴에서는 `Iterator` 패턴이라고 한다.
- 집합체(Aggregate)의 내부 구조를 노출하지 않고, 원소를 순차적으로 접근할 수 있게 해주는 패턴이다.

## 핵심 원칙

- **순회 로직의 분리**: 컬렉션 자체와 순회 방법을 별도 객체로 분리한다.
- **구현에 독립적인 순회**: `BookShelf`의 내부가 배열이든 `ArrayList`든, 순회하는 코드는 변경 없이 동작한다.
- **단일 책임**: 컬렉션은 데이터 저장, Iterator는 순회만 담당한다.

## 클래스 다이어그램

```
  <<interface>>              <<interface>>
  Iterable<Book>             Iterator<Book>
  +--------------+           +------------------+
  | + iterator() |           | + hasNext(): bool |
  +--------------+           | + next(): Book    |
        △                    +------------------+
        |                            △
        |                            |
  +------------+        creates   +-------------------+
  | BookShelf  |----------------->| BookShelfIterator |
  +------------+                  +-------------------+
  | - books    |                  | - bookShelf       |
  +------------+                  | - index           |
  | + appendBook()  |            +-------------------+
  | + getBookAt()   |            | + hasNext()       |
  | + getLength()   |            | + next()          |
  | + iterator()    |            +-------------------+
  +------------------+
        |
        | has many
        v
  +------------+
  |    Book    |
  +------------+
  | - name     |
  +------------+
  | + getName()|
  +------------+
```

## 역할 정리

| 역할 | 클래스/인터페이스 | 설명 |
|------|-------------------|------|
| Iterator (추상) | `java.util.Iterator<Book>` | 순회에 필요한 `hasNext()`, `next()` 정의 |
| ConcreteIterator | `BookShelfIterator` | 실제 순회 로직 구현, index로 위치 추적 |
| Aggregate (추상) | `java.lang.Iterable<Book>` | `iterator()` 메서드 정의 |
| ConcreteAggregate | `BookShelf` | 집합체, `Iterator` 생성을 담당 |
| Element | `Book` | 순회 대상 요소 |

## 현재 구현사항

### Book
- 책 이름(`name`)을 가지는 단순한 값 객체
- 생성자에서 이름을 받고, `getName()`으로 조회

### BookShelf
- `ArrayList<Book>`으로 책을 저장하는 집합체
- `Iterable<Book>` 구현 → 향상된 for문 사용 가능
- 기본 생성자 + 초기 용량 지정 생성자 제공

### BookShelfIterator
- `Iterator<Book>` 구현 → 표준 순회 규약 준수
- `index`로 현재 위치를 추적하며 순차 접근
- `hasNext()` false일 때 `next()` 호출 시 `NoSuchElementException` 발생

## 사용 예시

```java
BookShelf bookShelf = new BookShelf();
bookShelf.appendBook(new Book("On Liberty"));
bookShelf.appendBook(new Book("Don Quixote"));
bookShelf.appendBook(new Book("Walden"));
bookShelf.appendBook(new Book("Silent Spring"));

// 향상된 for문 (내부적으로 Iterator 사용)
for (Book book : bookShelf) {
    System.out.println(book.getName());
}

// 명시적 Iterator 사용
Iterator<Book> it = bookShelf.iterator();
while (it.hasNext()) {
    Book book = it.next();
    System.out.println(book.getName());
}
```

## Iterator 패턴을 쓰는 이유

**"구현과 순회를 분리하기 위해서"**

```java
// 이 코드는 BookShelf 내부가 배열이든 ArrayList든 동일하게 동작한다
for (Book book : bookShelf) {
    System.out.println(book.getName());
}
```

내부 구현을 `Book[]` 배열에서 `ArrayList<Book>`으로 변경해도 순회하는 쪽 코드는 수정할 필요가 없다.