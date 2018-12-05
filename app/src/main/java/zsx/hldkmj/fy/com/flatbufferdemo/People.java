package zsx.hldkmj.fy.com.flatbufferdemo;// automatically generated, do not modify

import java.nio.*;
import java.lang.*;
import java.util.*;

public class People extends Table {
  public static People getRootAsPeople(ByteBuffer _bb) { return getRootAsPeople(_bb, new People()); }
  public static People getRootAsPeople(ByteBuffer _bb, People obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public People __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public int pid() { int o = __offset(4); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public String name() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public int age() { int o = __offset(8); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public String occupation() { int o = __offset(10); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer occupationAsByteBuffer() { return __vector_as_bytebuffer(10, 1); }
  public Friend friends(int j) { return friends(new Friend(), j); }
  public Friend friends(Friend obj, int j) { int o = __offset(12); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int friendsLength() { int o = __offset(12); return o != 0 ? __vector_len(o) : 0; }

  public static int createPeople(FlatBufferBuilder builder,
      int pid,
      int name,
      int age,
      int occupation,
      int friends) {
    builder.startObject(5);
    People.addFriends(builder, friends);
    People.addOccupation(builder, occupation);
    People.addAge(builder, age);
    People.addName(builder, name);
    People.addPid(builder, pid);
    return People.endPeople(builder);
  }

  public static void startPeople(FlatBufferBuilder builder) { builder.startObject(5); }
  public static void addPid(FlatBufferBuilder builder, int pid) { builder.addInt(0, pid, 0); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(1, nameOffset, 0); }
  public static void addAge(FlatBufferBuilder builder, int age) { builder.addInt(2, age, 0); }
  public static void addOccupation(FlatBufferBuilder builder, int occupationOffset) { builder.addOffset(3, occupationOffset, 0); }
  public static void addFriends(FlatBufferBuilder builder, int friendsOffset) { builder.addOffset(4, friendsOffset, 0); }
  public static int createFriendsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startFriendsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endPeople(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
  public static void finishPeopleBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
};

