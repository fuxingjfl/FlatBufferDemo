package zsx.hldkmj.fy.com.flatbufferdemo;// automatically generated, do not modify


import java.nio.*;
import java.lang.*;
import java.util.*;

public class Friend extends Table {
  public static Friend getRootAsFriend(ByteBuffer _bb) { return getRootAsFriend(_bb, new Friend()); }
  public static Friend getRootAsFriend(ByteBuffer _bb, Friend obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public Friend __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public int fid() { int o = __offset(4); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public String relationship() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer relationshipAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }

  public static int createFriend(FlatBufferBuilder builder,
      int fid,
      int relationship) {
    builder.startObject(2);
    Friend.addRelationship(builder, relationship);
    Friend.addFid(builder, fid);
    return Friend.endFriend(builder);
  }

  public static void startFriend(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addFid(FlatBufferBuilder builder, int fid) { builder.addInt(0, fid, 0); }
  public static void addRelationship(FlatBufferBuilder builder, int relationshipOffset) { builder.addOffset(1, relationshipOffset, 0); }
  public static int endFriend(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

