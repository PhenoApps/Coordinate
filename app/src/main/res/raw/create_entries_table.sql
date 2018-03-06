CREATE TABLE entries
(
    _id     INTEGER PRIMARY KEY AUTOINCREMENT,
    grid    INTEGER,
    row     INTEGER CHECK(row >= 1),
    col     INTEGER CHECK(col >= 1),
    edata   TEXT,
    stamp   INTEGER
)