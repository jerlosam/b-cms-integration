package io.crazy88.beatrix.e2e.visual;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Snapshot {

    private String name;

    private File image;
}
