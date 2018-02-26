CREATE TABLE grids
(
    _id         INTEGER PRIMARY KEY AUTOINCREMENT,
    temp        INTEGER,
    projectId   INTEGER,
    person      TEXT,
    activeRow   INTEGER CHECK(activeRow >= 0),
    activeCol   INTEGER CHECK(activeCol >= 0),
    options     TEXT,
    stamp       INTEGER
)