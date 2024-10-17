Тема - Система за филми, сериали и актьори

Стак - Spring Boot, Spring Security, Spring Data JPA, PostgreSQL, gRPC, JWT

Архитектура - Проектът се състои от: gRPC сървис, gRPC клиент и потребителски интерфейс

gRPC сървис - в него се съдържат .proto файловете, ентити класовете и техните репозиторита свързани с базата данни, utility клас за JWT и константи с информация отностно токена, имплементации на генерираните сървиси от .proto файловете и interceptor, който проверява за валиден токен.

gRPC клиент - включва REST контролери, DTO класове, филтър за JWT, клиент сървиси и два utility класа


Начин на работа на проекта:

1. HTTP заявка се праща от потребителя към REST контролер в gRPC клиента
2. Минава през филтър, взима се "Authorization" хедъра и се запазва
3. gRPC клиента праща заявка към gRPC сървиса като се подава и токена
4. Заявката минава през interceptor, който проверява дали токена е валиден
5. Стига се до имплементацията на генерирания сървис от .proto файла и се обработва заявката

Главни модели:

enum Genre {
  UNKNOWN_GENRE = 0;
  ACTION = 1;
  COMEDY = 2;
  DRAMA = 3;
  HORROR = 4;
  SCI_FI = 5;
  ROMANCE = 6;
}

message Actor {
  int32 id = 1;
  string name = 2;
  string nationality = 3;
  string birth_date = 4;
  double rating = 5;
  bool alive = 6;
  repeated string awards = 7;
}

message Movie {
  int32 id = 1;
  string title = 2;
  Genre genre = 3;
  string release_year = 4;
  float duration_minutes = 5;
  double rating = 6;
}

message TVSeries {
  int32 id = 1;
  string title = 2;
  Genre genre = 3;
  int32 seasons = 4;
  bool ongoing = 5;
  double rating = 6;
}
