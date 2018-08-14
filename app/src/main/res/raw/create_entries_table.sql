CREATE TABLE entries
(
    _id     INTEGER PRIMARY KEY AUTOINCREMENT,
    grid    INTEGER NOT NULL,
    row     INTEGER NOT NULL CHECK(row >= 1),
    col     INTEGER NOT NULL CHECK(col >= 1),
    edata   TEXT,
    stamp   INTEGER
)