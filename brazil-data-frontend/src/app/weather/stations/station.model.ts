export interface Station {
  id: number;
  region: string;
  state: string;
  wmoCode: string;
  position: Point;
  altitude: number;
  foundationDate: Date;
  name: string;
}

interface Point {
  latitude: number;
  longitude: number;
}
